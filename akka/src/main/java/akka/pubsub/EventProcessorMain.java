package akka.pubsub;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Processor 启动器
 */
public class EventProcessorMain {

    public static void main(String[] args) {
        final String port = args.length > 0 ? args[0] : "0";

        final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles= [processor]"))
                .withFallback(ConfigFactory.load("pubsub"));

        final ActorSystem system = ActorSystem.create("event-cluster-system", config);

        ActorRef processingActor = system.actorOf(Props.create(EventProcessor.class), "processingActor");

        system.log().info("Processing Actor: " + processingActor);
    }
}
