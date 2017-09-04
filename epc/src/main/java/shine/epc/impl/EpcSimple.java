package shine.epc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shine.epc.EpcEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简单的事件处理中心
 * 按时间顺序排队
 */
public class EpcSimple extends BaseEpc{

    private final static Logger log = LoggerFactory.getLogger(EpcSimple.class);
    private ExecutorService es;
    private boolean isShutdown;

    EpcSimple() {
        es = Executors.newFixedThreadPool(1);
        isShutdown = false;
    }

    @Override
    public void pushEvent(EpcEvent event) {
        if (isShutdown) return;
        try {
            es.submit(new Task(event));
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
