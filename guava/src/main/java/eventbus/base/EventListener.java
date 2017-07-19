package eventbus.base;


import com.google.common.eventbus.Subscribe;

public class EventListener {

    public int lastMessage = 0;

    @Subscribe
    public void listen(Message event) {
        lastMessage = event.getMessage();
        System.out.println("Message:"+lastMessage);
    }

    public int getLastMessage() {
        return lastMessage;
    }
}
