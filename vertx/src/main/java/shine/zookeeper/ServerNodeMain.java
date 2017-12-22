package shine.zookeeper;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import shine.ignite.example.verticle.ModelVerticle;

/**
 * @description: 节点
 * @author : 7le
 * @date: 2017/12/5
 */
public class ServerNodeMain {

    public static void main(String[] args) {
        //创建 ZookeeperClusterManager
        ClusterManager mgr = new ZookeeperClusterManager();
        // 设置集群管理器
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new ConsumerVerticle());
                System.out.println("===================================节点启动成功");
            } else {
                System.out.println("===================================节点启动失败");
            }
        });
    }
}
