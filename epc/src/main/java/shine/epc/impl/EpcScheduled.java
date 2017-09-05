package shine.epc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shine.epc.EpcEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class EpcScheduled extends BaseEpc {
    private final static Logger log = LoggerFactory.getLogger(EpcScheduled.class);
    private ScheduledExecutorService es;
    private boolean isShutdown;

    EpcScheduled() {
        es = Executors.newScheduledThreadPool(1);
        isShutdown = false;
    }


    @Override
    @Deprecated
    public void pushEvent(EpcEvent event, Collision collision) {
        if (isShutdown) return;
        try {
            es.schedule(new Task(event, collision), 0, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error("execute event error:", ex);
        }
    }

    public void pushEvent(EpcEvent event, Collision collision, long delay, TimeUnit unit) {
        if (isShutdown) return;
        try {
            es.schedule(new Task(event, collision), delay, unit);
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
