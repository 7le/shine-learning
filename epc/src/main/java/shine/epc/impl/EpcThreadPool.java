package shine.epc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shine.epc.EpcEvent;

import java.util.Enumeration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * EPC 线程池
 */
public class EpcThreadPool extends BaseEpc {

    private final static Logger LOG = LoggerFactory.getLogger(EpcThreadPool.class);

    /**
     * 缺省大小为 cpu个数的 2倍
     */
    static final int DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 缺省最大线程数为 cpu个数的4倍
     */
    static final int DEFAULT_MAX_SIZE = Runtime.getRuntime().availableProcessors() * 4;

    /**
     * 线程池维护线程的最小数量
     */
    int corePoolSize = DEFAULT_POOL_SIZE;

    /**
     * 线程池维护线程的最大数量
     */
    int maxPoolSize = DEFAULT_MAX_SIZE;

    /**
     * 执行任务的线程池，事件是伪装成Task(任务)执行的。
     */
    ThreadPoolExecutor executor = null;

    /**
     * 主队列，用于存放尚未处理的(事件)任务的队列
     */
    ConcurrentLinkedQueue<Task> mainQueue = null;

    /**
     * 冲突等待队列表，用于存放等待同类型冲突(Collision)处理完成后，再执行的任务队列；每种冲突(Collision)一个队列
     */
    ConcurrentHashMap<Collision, ConcurrentLinkedQueue<Task>> waitingCollsQueueMap = null;

    /**
     * 冲突执行表，用于存放正在执行任务的占用冲突(Collision)；占用冲突被释放后，等待的任务才能执行
     */
    ConcurrentHashMap<Collision, Task> runningCollsMap = null;

    /**
     * 正在处理的任务数量,逻辑上应保证该数字 不大于  线程的数量(poolSize)
     */
    AtomicInteger runningTaskNum = new AtomicInteger(0);

    /**
     * 加入event队列锁，防止多线程加入Event时，出现错误。
     */
    ReentrantLock pushLock = new ReentrantLock();

    /**
     * 等待执行的顶级任务。
     */
    Task topTask = null;
    /**
     * 是否关闭
     */
    boolean isShutdown = false;

    EpcThreadPool() {
        this(DEFAULT_POOL_SIZE, DEFAULT_MAX_SIZE);
    }

    EpcThreadPool(int pool) {
        this(pool, DEFAULT_MAX_SIZE);
    }

    /**
     * 初始化
     * @param pool
     * @param max
     */
    EpcThreadPool(int pool, int max) {
        this.corePoolSize = (pool < 1) ? DEFAULT_POOL_SIZE : pool;
        this.maxPoolSize = (max < 1) ? DEFAULT_MAX_SIZE : max;
        this.maxPoolSize = (maxPoolSize < corePoolSize) ? corePoolSize : maxPoolSize;

        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new DefaultEpcThreadFactory());

        mainQueue = new ConcurrentLinkedQueue<Task>();
        waitingCollsQueueMap = new ConcurrentHashMap<Collision, ConcurrentLinkedQueue<Task>>();
        runningCollsMap = new ConcurrentHashMap<Collision, Task>();
    }

    /**
     * EPC 线程工厂
     */
    static class DefaultEpcThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        DefaultEpcThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "EPC-" +
                    poolNumber.getAndIncrement() +
                    "-worker-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    @Override
    public void pushEvent(EpcEvent event, Collision collision) {
        if (isShutdown) return;
        if (event == null)
            throw new NullPointerException("Event is null.");
        Task task = new Task(event, collision);

        //开始等待任务计时
        task.beginProfile();

        mainQueue.offer(task);

        // 检测主队列是否有可执行的事件
        checkMainQueue();
    }

    // 开始执行任务前的准备工作
    private void beginTask(Task task) {
        // 结束等待任务计时
        task.endProfile();

        // EPC已被强制关闭
        if (executor.isTerminating())
            return;

        // 正在处理的任务数 加一
        runningTaskNum.incrementAndGet();

        //开始执行任务计时
        task.beginProfile();
        // 执行任务
        executor.execute(task);
    }

    // 检测主队列是否有可执行的事件
    private void checkMainQueue() {
        int cur = runningTaskNum.get();// 当前并发执行的任务数
        if (cur >= maxPoolSize)//已到最大任务数，等待有任务执行完后，再执行。
            return;

        pushLock.lock();
        try {
            cur = runningTaskNum.get();// 此处的并发数可能已经改变
            if (topTask != null) {// 有顶级任务 正在等候
                if (cur == 0) {// 当前没有任务在执行
                    cur++;//并发任务数 加一
                    beginTask(topTask);// 执行任务
                }
            }

            while ((topTask == null) // 没有顶级冲突体等待执行
                    && (!mainQueue.isEmpty()) // 主队列不为空
                    && (cur < maxPoolSize)) { // 正在运行的任务数 小于 线程池数
                Task task = mainQueue.poll();
                Collision collision = task.getCollision();
                if (collision == null) {// 无冲突要求的任务，直接加入执行池
                    cur++;//并发任务数 加一
                    beginTask(task);// 执行任务
                } else if (collision.equals(Collision.TOP_COLLISION)) {// 该任务为 顶级冲突任务
                    // 需要等待当前在执行或在等待的任务执行 执行完后，再执行顶级冲突任务
                    topTask = task;
                    if (cur == 0) {// 当前没有任务在执行
                        cur++;//并发任务数 加一
                        beginTask(task);// 执行任务
                    }
                    return;// 结束循环，因为顶级冲突任务 执行期间 其它任务都不容许执行
                } else if (runningCollsMap.containsKey(collision)) {// 正在运行的任务有同样的冲突
                    if (waitingCollsQueueMap.containsKey(collision)) {// 有类似冲突在等待的，加入等待队列
                        waitingCollsQueueMap.get(collision).offer(task);
                    } else {//之前没有的话， 新建一个队列
                        ConcurrentLinkedQueue<Task> queue = new ConcurrentLinkedQueue<Task>();
                        queue.offer(task);
                        waitingCollsQueueMap.put(collision, queue);
                    }
                } else {// 有冲突要求，但没有类似冲突任务 在执行的，加入执行池，并记入执行冲突表中
                    cur++;
                    beginTask(task);
                }
            }// end while
        } finally {
            pushLock.unlock();
        }
    }

    @Override
    public void shutdown() {
        isShutdown = true;
        executor.shutdown();
    }

    @Override
    public void shutdownNow() {
        isShutdown = true;
        executor.shutdownNow();
        mainQueue.clear();
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getWaitingCollsQueueMapLen() {
        return waitingCollsQueueMap.size();
    }

    public String getWaitingCollsQueueMapDetail() {
        Enumeration<Collision> enu = waitingCollsQueueMap.keys();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        while(enu.hasMoreElements()) {
            Collision col = enu.nextElement();
            sb.append(col);
            sb.append("=");
            ConcurrentLinkedQueue<Task> queue =  waitingCollsQueueMap.get(col);
            sb.append(queue == null? "null" : queue.size());
            sb.append(";");
        }
        sb.append(")");

        return sb.toString();
    }

    public int getRunningCollsMapLen() {
        return runningCollsMap.size();
    }

    public int getMainQueueLen() {
        return mainQueue.size();
    }

    public int getRunningTaskNum() {
        return runningTaskNum.get();
    }

    public long getTaskCount() {
        return executor.getTaskCount();
    }

    public long getCompletedTaskCount() {
        return executor.getCompletedTaskCount();
    }

    public int getAticveCount() {
        return executor.getActiveCount();
    }

    public int getThreadPoolQueueSize() {
        return executor.getQueue().size();
    }

    String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(";getWaitingCollsQueueMapLen=");
        sb.append(getWaitingCollsQueueMapLen());
        sb.append(";getRunningCollsMapLen=");
        sb.append(getRunningCollsMapLen());
        sb.append(";getMainQueueLen=");
        sb.append(getMainQueueLen());
        sb.append(";getTaskCount=");
        sb.append(getTaskCount());
        sb.append(";getCompletedTaskCount=");
        sb.append(getCompletedTaskCount());
        sb.append(";getAticveCount=");
        sb.append(getAticveCount());
        sb.append(";getThreadPoolQueueSize=");
        sb.append(getThreadPoolQueueSize());
        sb.append(";getWaitingCollsQueueMapDetail=");
        sb.append(getWaitingCollsQueueMapDetail());

        return sb.toString();
    }
}
