package akka.cluster.complete.fe;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author 7le
 * @Description: main
 * @date 2017年10月27日
 * @since v1.0.0
 */
public class FrontendMain {

    public static void main(String[] args) {
        final int upToN = 10;
        final Config config = ConfigFactory.parseString(
                "akka.cluster.roles = [frontend]").withFallback(
                ConfigFactory.load("cluster1"));
        final ActorSystem system = ActorSystem.create("ClusterSystem", config);
        system.log().info(
                "Factorials will start when 2 backend members in the cluster.");
        //加入集群成功后的回调事件
        Cluster.get(system).registerOnMemberUp(() ->
                system.actorOf(Props.create(Frontend.class, upToN, true), "frontend"));
    }
}
