package akka.cluster.complete.be;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author 7le
 * @Description: main
 * @date 2017年10月27日
 * @since v1.0.0
 */
public class BackendMain {

    public static void main(String[] args) {
        // 重写配置文件中的集群角色和端口
        final String port = args.length > 0 ? args[0] : "0";
        final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
                withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
                withFallback(ConfigFactory.load("cluster1"));
        ActorSystem system = ActorSystem.create("ClusterSystem", config);
        system.actorOf(Props.create(Backend.class), "backend");
    }
}
