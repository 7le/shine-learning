package shine.epc.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shine.epc.EpcEvent;

import java.util.concurrent.*;

/**
 * 简单的事件处理中心
 * 按时间顺序排队
 */
public class EpcSimple extends BaseEpc {

    private final static Logger log = LoggerFactory.getLogger(EpcSimple.class);
    private ExecutorService es;
    private boolean isShutdown;

    EpcSimple() {
        es = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),new ThreadFactoryBuilder().setNameFormat("EpcSimple-").build());
        isShutdown = false;
    }

    @Override
    public void pushEvent(EpcEvent event, Collision collision) {
        if (isShutdown) {
            return;
        }
        try {
            es.submit(new Task(event, collision));
        } catch (Exception ex) {
            log.error("execute event error:", ex);
        }
    }

    @Override
    public void shutdown() {
        isShutdown = true;
        es.shutdown();
    }

    @Override
    public void shutdownNow() {
        isShutdown = true;
        es.shutdownNow();
    }
}
