package shine.spring.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import shine.spring.annotation.EventSubscriber;
import shine.spring.dao.model.Video;
import shine.spring.service.VideoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 7le on 2017/11/6
 */
@EventSubscriber
public class VideoHandler {

    @Autowired
    private VideoService videoService;

    private List<Object> buf = new ArrayList<Object>();

    private static final int MAX = 100;

    @AllowConcurrentEvents
    @Subscribe
    public void onVideo(Video video) {
        List<Object> toBeFlush = null;

        synchronized (this) {
            buf.add(video);
            if (buf.size() > MAX) {
                toBeFlush = new ArrayList<Object>(buf);
                buf.clear();
            }
        }
        if (toBeFlush != null && toBeFlush.size() > 0) {
            System.out.println(Thread.currentThread() + " flush:" + toBeFlush.size());
            // System.out.println(max);
            System.out.println("调用service后续操作："+videoService);
        }
    }
}
