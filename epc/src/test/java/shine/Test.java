package shine;


import shine.epc.Epc;
import shine.epc.EpcEvent;
import shine.epc.impl.EpcFactory;

/**
 * 入口
 */
public class Test {

    private static Epc epc;

    public static void main(String[] args) {
        epc = EpcFactory.createThreadPoolEpc();
        EpcEvent event = new Task();
        epc.pushEvent(event);
    }
}
