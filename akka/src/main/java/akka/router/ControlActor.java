package akka.router;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatcher.StartCommand;
import akka.dispatcher.WriterActor;
import akka.routing.FromConfig;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hq
 * @Description: Akka通过Router机制,来有效的分配消息给actor
 * @date 2017年10月19日
 * @since v1.0.0
 */
public class ControlActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartCommand.class, startCommand -> {
                    List<ActorRef> actors = createActors(startCommand.getActorCount());
                    Router router = new Router(new RoundRobinRoutingLogic());
                    for (ActorRef actor : actors) {
                        router = router.addRoutee(actor);
                        //需要注意,需要接收addRoutee的返回
                    }
                    router.route("Insert",ActorRef.noSender());
                })
                .build();
    }

    private List<ActorRef> createActors(int actorCount) {
        Props props = Props.create(WriterActor.class).withDispatcher("writer-dispatcher");

        List<ActorRef> actors = new ArrayList<>(actorCount);
        for (int i = 0; i < actorCount; i++) {
            actors.add(getContext().actorOf(FromConfig.getInstance().props(Props.create(StartCommand.class)),
                    "router2"));
        }
        return actors;
    }
}
