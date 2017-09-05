package shine;


import shine.epc.Epc;
import shine.epc.EpcEvent;
import shine.epc.impl.BaseEpc;
import shine.epc.impl.EpcFactory;
import shine.epc.impl.EpcScheduled;

import java.util.concurrent.TimeUnit;

/**
 * 入口
 */
public class Test {

    private static Epc epc;
    private static Epc simple;
    private static EpcScheduled scheduled;

    public static void main(String[] args) {
        epc = EpcFactory.createEpcThreadPool();
        EpcEvent event = new Task();
        epc.pushEvent(event, null);

        simple = EpcFactory.createEpcSimple();
        EpcEvent epcEvent = new Task();
        simple.pushEvent(epcEvent, null);

        scheduled = EpcFactory.createEpcScheduled();
        EpcEvent epcEvent1 = new Task();
        scheduled.pushEvent(epcEvent1, null, 20L, TimeUnit.SECONDS);
        scheduled.pushEvent(epcEvent1, null, 5L, TimeUnit.SECONDS);
    }
}
