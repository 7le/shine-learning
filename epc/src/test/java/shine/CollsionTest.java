package shine;

import shine.epc.Epc;
import shine.epc.EpcEvent;
import shine.epc.impl.Collision;
import shine.epc.impl.EpcFactory;

/**
 * 冲突测试入口
 */
public class CollsionTest {

    private static Epc epc;

    public static void main(String[] args) {
        epc = EpcFactory.createEpcThreadPool();
        EpcEvent event = new Task();
        Collision collision=Collision.generateCollision("yoyo");
        epc.pushEvent(event, collision);
        Collision collision1=Collision.generateCollision("yo");
        epc.pushEvent(event, collision);
        epc.pushEvent(event, collision1);
    }
}
