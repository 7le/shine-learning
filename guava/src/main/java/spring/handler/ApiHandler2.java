package spring.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import spring.annotation.EventSubscriber;

@EventSubscriber
public class ApiHandler2 {


    @Subscribe
    @AllowConcurrentEvents
    public void logEvent2(Integer event) {
        System.out.println("receive a event2:"+event);
    }

}
