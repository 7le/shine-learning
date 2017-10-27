package akka.dispatcher;

import akka.actor.AbstractActor;

/**
 * @author 7le
 * @date 2017年10月19日
 * @since v1.0.0
 */
public class WriterActor extends AbstractActor{

    @Override
    public Receive createReceive() {
        System.out.println(Thread.currentThread().getName());
        return receiveBuilder()
                .build();
    }
}
