package akka.dispatcher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * @author 7le
 * @date 2017年10月19日
 * @since v1.0.0
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("dispatcher", ConfigFactory.load("dispatcher.conf")
                .getConfig("dispatcher"));

        // 创建一个到greeter Actor的管道
        final ActorRef controlActor = system.actorOf(Props.create(ControlActor.class), "control");

        controlActor.tell(new StartCommand(1000),ActorRef.noSender());

        //system.shutdown();
    }
}
