package spring.handler;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import spring.annotation.EventSubscriber;

/**
 * Dead Event：
 * 如果EventBus发送的消息都不是订阅者关心的称之为Dead Event。
 * Created by 7le on 2017/11/6
 */
@EventSubscriber
public class DeadEventListener {

    boolean notDelivered = false;

    @Subscribe
    public void listen(DeadEvent event) {

        notDelivered = true;
        System.out.println("DeadEvent: "+event);
    }

    public boolean isNotDelivered() {
        return notDelivered;
    }
}
