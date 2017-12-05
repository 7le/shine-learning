package shine.spring.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import shine.spring.annotation.EventSubscriber;
import shine.spring.dao.model.Video;
import shine.spring.service.VideoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;

/**
 * @description: eventbus handler
 * @author : 7le
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

    @AllowConcurrentEvents
    @Subscribe
    public void onVideo(Video video) {
        List<Object> toBeFlush = null;

        long write = lock.writeLock();
        try {
            buf.add(video);
            System.out.println(Thread.currentThread().getName() + " buf : " + buf.size());
            if (buf.size() > MAX || checkTime()) {
                toBeFlush = new ArrayList<>(buf);
                buf.clear();
            }
            if (toBeFlush != null && toBeFlush.size() > 0) {
                System.out.println(Thread.currentThread() + " flush:" + toBeFlush.size());
                // System.out.println(max);
                System.out.println("调用service后续操作："+videoService);
            }
        } finally {
            lock.unlockWrite(write);
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
