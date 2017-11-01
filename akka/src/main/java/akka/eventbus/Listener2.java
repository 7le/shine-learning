package akka.eventbus;

import akka.actor.AbstractActor;

/**
 * 监听
 */
public class Listener2 extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(AllKindsOfMusic.Jazz.class, msg -> System.out.printf("NO2：%s is listening to: %s%n", getSelf().path().name(), msg))
                .match(AllKindsOfMusic.Electronic.class, msg -> System.out.printf("NO2：%s is listening to: %s%n", getSelf().path().name(), msg))
                .build();
    }
}
