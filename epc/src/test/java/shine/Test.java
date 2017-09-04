package shine;


import shine.epc.Epc;
import shine.epc.EpcEvent;
import shine.epc.impl.EpcFactory;

/**
 * 入口
 */
public class Test {

    private static Epc epc;
    private static Epc simple;

    public static void main(String[] args) {
        epc = EpcFactory.createEpcThreadPool();
        EpcEvent event = new Task();
        epc.pushEvent(event);

        simple=EpcFactory.createEpcSimple();
        EpcEvent epcEvent=new Task();
        simple.pushEvent(event);
    }
}
