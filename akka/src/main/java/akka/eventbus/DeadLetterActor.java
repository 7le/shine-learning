package akka.eventbus;

import akka.actor.AbstractActor;
import akka.actor.DeadLetter;

/**
 *
 */
public class DeadLetterActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DeadLetter.class, System.out::println)
                .build();
    }
}