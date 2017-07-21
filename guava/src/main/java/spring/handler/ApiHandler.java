package spring.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import spring.annotation.EventSubscriber;

@EventSubscriber
public class ApiHandler {


    @Subscribe
    @AllowConcurrentEvents
    public void logEvent(String event) {
        System.out.println("receive a event:"+event);
    }

}
