package shine.spring.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import shine.spring.annotation.EventSubscriber;
import shine.spring.dao.model.Video;
import shine.spring.service.VideoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;

/**
 * @author : 7le
 * @description: eventbus handler
 * @date: 2017/11/6
 */
@EventSubscriber
public class VideoHandler {

    @Autowired
    private VideoService videoService;

    private List<Object> buf = new ArrayList<Object>();

    private long lastTime = 0L;

    private final StampedLock lock = new StampedLock();

    /**
     * 达到最大数量就写库
     */
    private static final int MAX = 49;

    /**
     * 最大写库等待时间 毫秒
     */
    private static final long TIME = 1000;

    public VideoHandler() {
        /*ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("video-handler-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory);
        executor.execute(() -> {
            while (true) {
                if (checkTime()) {
                    List<Object> toBeFlush = null;
                    long write = lock.writeLock();
                    try {
                        toBeFlush = new ArrayList<>(buf);
                        buf.clear();
                    } finally {
                        lock.unlockWrite(write);
                    }
                    if (toBeFlush.size() > 0) {
                        System.out.println(Thread.currentThread() + " flush:" + toBeFlush.size());
                        System.out.println("调用service后续操作：" + videoService);
                    }
                    System.out.println("======================================video: " + Thread.currentThread().getName());
                }
            }
        });*/
        //newScheduledThreadPool 底层用Condition await() 和signal() 来实现了线程间的等待
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        //周期性地执行任务，延迟0s后，每1s一次地周期性执行任务
        scheduledThreadPool.scheduleAtFixedRate(() -> {
                    List<Object> toBeFlush = null;
                    long write = lock.writeLock();
                    try {
                        toBeFlush = new ArrayList<>(buf);
                        buf.clear();
                    } finally {
                        lock.unlockWrite(write);
                    }
                    if (toBeFlush.size() > 0) {
                        System.out.println(Thread.currentThread() + " flush:" + toBeFlush.size());
                        System.out.println("调用service后续操作：" + videoService);
                    }
                    System.out.println("1======================================video: " + Thread.currentThread().getName());

                },
                0, 1, TimeUnit.SECONDS);

        scheduledThreadPool.scheduleAtFixedRate(() -> {
                    List<Object> toBeFlush = null;
                    long write = lock.writeLock();
                    try {
                        toBeFlush = new ArrayList<>(buf);
                        buf.clear();
                    } finally {
                        lock.unlockWrite(write);
                    }
                    if (toBeFlush.size() > 0) {
                        System.out.println(Thread.currentThread() + " flush:" + toBeFlush.size());
                        System.out.println("调用service后续操作：" + videoService);
                    }
                    System.out.println("2======================================video: " + Thread.currentThread().getName());

                },
                0, 1, TimeUnit.SECONDS);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void onVideo(Video video) {
        List<Object> toBeFlush = null;

        long write = lock.writeLock();
        try {
            buf.add(video);
            System.out.println(Thread.currentThread().getName() + " buf : " + buf.size());
            if (buf.size() > MAX) {
                toBeFlush = new ArrayList<>(buf);
                buf.clear();
            }
        } finally {
            lock.unlockWrite(write);
        }
        if (toBeFlush != null && toBeFlush.size() > 0) {
            System.out.println(Thread.currentThread() + " flush:" + toBeFlush.size());
            // System.out.println(max);
            System.out.println("调用service后续操作：" + videoService);
        }
    }

    /**
     * 设置最大写库时间
     *
     * @return
     */
    private boolean checkTime() {
        if (lastTime == 0) {
            lastTime = System.currentTimeMillis();
            return false;
        } else {
            if (System.currentTimeMillis() - lastTime >= TIME) {
                lastTime = System.currentTimeMillis();
                return true;
            } else {
                return false;
            }
        }
    }
}
