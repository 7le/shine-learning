package shine.epc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shine.epc.EpcEvent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * EPC 线程池
 */
public class EpcThreadPool extends BaseEpc{

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
     * 是否关闭
     */
    boolean isShutdown = false;

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
    }

    /**
     *  EPC 线程工厂
     */
    /**
     * The default EPC thread factory
     */
    static class DefaultEpcThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        DefaultEpcThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
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
    public void pushEvent(EpcEvent event) {
        if (isShutdown) return;
        if (event == null)
            throw new NullPointerException("Event is null.");
        Task task = new Task(event);

        mainQueue.offer(task);

        executor.execute(task);
    }

    @Override
    public void shutdown(EpcEvent event) {
        isShutdown = true;
        executor.shutdown();
    }

    @Override
    public void shutdownNow(EpcEvent event) {
        isShutdown = true;
        executor.shutdownNow();
        mainQueue.clear();
    }
}
