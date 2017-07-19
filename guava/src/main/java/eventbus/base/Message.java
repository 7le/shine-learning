package eventbus.base;

public class Message {

    private final int msg;

    public Message(int msg) {
        this.msg = msg;
        System.out.println("event message:"+msg);
    }

    public int getMessage() {
        return msg;
    }

}
