package shine.spring.annotation;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 7le on 2017/11/6
 */
@Service
public class EventBusService extends ApplicationObjectSupport implements InitializingBean {

    private EventBus innerBus;

    public void unRegister(Object eventListener){
        innerBus.unregister(eventListener);
    }

    public void postEvent(Object event){
        innerBus.post(event);
    }

    public void register(Object eventListener){
        innerBus.register(eventListener);
    }

    private static final ThreadPoolExecutor EXECUTOR =  new ThreadPoolExecutor(8, 50,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactoryImpl("pool-api-handler-thread-"));

    private static class ThreadFactoryImpl implements ThreadFactory {
        private String prex;
        private AtomicInteger index = new AtomicInteger(0);
        ThreadFactoryImpl(String prex) {
            this.prex = prex;
        }
        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, prex + index.getAndIncrement());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        innerBus = new AsyncEventBus(EXECUTOR);
        getApplicationContext().getBeansWithAnnotation(EventSubscriber.class)
                .forEach((name, bean) -> innerBus.register(bean));
    }
}
