package shine.spring.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.impl.VertxInternal;
import io.vertx.core.spi.cluster.ClusterManager;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import shine.spring.util.IgniteCacheUtils;
import shine.spring.vertx.util.VerticleLauncher;

import java.util.UUID;

/**
 * HttpVerticle
 * Created by 7le on 2017/11/6
 */
public class HttpVerticle extends AbstractVerticle{

    private static Ignite ignite;

    static public void deploy(){
        VerticleLauncher.deployVerticleWithDefaultConfig(HttpVerticle.class,true);
    }

    @Override
    public void start() throws Exception {
        initIgnite();

        //可以继续加入Verticle
        getVertx().deployVerticle(VideoVerticle.class.getName());

    }


    /**
     * 初始化缓存
     */
    private void initIgnite() {
        if (ignite == null) {
            ClusterManager clusterManager = ((VertxInternal) vertx).getClusterManager();
            String uuid = clusterManager.getNodeID();
            ignite = Ignition.ignite(UUID.fromString(uuid));
            IgniteCacheUtils.initIgniteCache(ignite);
        }else {
            System.out.println("ignite is null !");
        }
    }

}
