package spring.annotation;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EventBusService extends ApplicationObjectSupport implements InitializingBean {

    private EventBus innerBus;

    public void unRegister(Object eventListener){
        innerBus.unregister(eventListener);
    }

    public void postEvent(String event){
        innerBus.post(event);
    }

    public void register(Object eventListener){
        innerBus.register(eventListener);
    }

    public static final ThreadPoolExecutor executor =  new ThreadPoolExecutor(0, 50,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactoryImpl("pool-api-handler-thread-"));

    private static class ThreadFactoryImpl implements ThreadFactory {
        private String prex;
        private AtomicInteger index = new AtomicInteger(0);
        public ThreadFactoryImpl(String prex) {
            this.prex = prex;
        }
        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, prex + index.getAndIncrement());
        }
    }

    public void afterPropertiesSet() throws Exception {
        innerBus = new AsyncEventBus(executor);
        getApplicationContext().getBeansWithAnnotation(EventSubscriber.class).forEach((name, bean) -> {
            innerBus.register(bean);
        });
    }
}
