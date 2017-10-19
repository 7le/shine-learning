package akka.dispatcher;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hq
 * @date 2017年10月19日
 * @since v1.0.0
 */
public class ControlActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartCommand.class, startCommand -> {
                    List<ActorRef> actors=createActors(startCommand.getActorCount());
                    actors.stream().parallel().forEach(
                            actorRef -> actorRef.tell("Insert",ActorRef.noSender()));
                })
                .build();
    }

    private List<ActorRef> createActors(int actorCount) {
        Props props = Props.create(WriterActor.class).withDispatcher("writer-dispatcher");

        List<ActorRef> actors = new ArrayList<>(actorCount);
        for (int i = 0; i < actorCount; i++) {
            actors.add(getContext().actorOf(props, "writer_" + i));
        }
        return actors;
    }
}
