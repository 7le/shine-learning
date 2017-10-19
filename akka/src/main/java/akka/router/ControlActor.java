package akka.router;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatcher.StartCommand;
import akka.routing.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hq
 * @date 2017年10月19日
 * @since v1.0.0
 */
public class ControlActor extends AbstractActor {

    Router router;
    {
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 5; i++) {
            ActorRef r = getContext().actorOf(Props.create(StartCommand.class));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartCommand.class, startCommand -> router.route(startCommand, getSender()))
                .build();
    }

    public static void main(String[] args) {
        final ActorSystem system= ActorSystem.create("router");
        // 创建一个到greeter Actor的管道

        ActorRef router = system.actorOf(FromConfig.getInstance().props(Props.create(StartCommand.class)), "router");


        router.tell(new StartCommand(10),ActorRef.noSender());
    }
}
