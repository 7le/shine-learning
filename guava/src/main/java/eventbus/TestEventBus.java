package eventbus;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

public class TestEventBus {

    @Test
    public void testReceiveEvent() throws Exception {

        EventBus eventBus = new EventBus("test");
        EventListener listener = new EventListener();

        eventBus.register(listener);

        eventBus.post(new Message(1));
        eventBus.post(new Message(2));
        eventBus.post(new Message(3));

        System.out.println("LastMessage:"+listener.getLastMessage());
    }

    @Test
    public void testMultipleEvents() throws Exception {

        EventBus eventBus = new EventBus("test");
        MultipleListener multiListener = new MultipleListener();

        eventBus.register(multiListener);

        eventBus.post(1);
        eventBus.post(2);
        eventBus.post(3);
        eventBus.post(4L);
        eventBus.post(5L);
        eventBus.post(6L);

        System.out.println("LastInteger:"+multiListener.getLastInteger());
        System.out.println("LastLong:"+multiListener.getLastLong());
    }

    @Test
    public void testDeadEventListeners() throws Exception {

        EventBus eventBus = new EventBus("test");
        DeadEventListener deadEventListener = new DeadEventListener();
        eventBus.register(deadEventListener);

        eventBus.post(new Message(1));
        eventBus.post(new Message(2));

        System.out.println("deadEvent:"+deadEventListener.isNotDelivered());

    }
}
