package shine.ignite.example;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.ignite.IgniteClusterManager;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.IgnitionEx;
import org.apache.ignite.internal.util.typedef.F;
import shine.ignite.example.verticle.HttpServerVerticle;
import shine.ignite.example.verticle.ModelVerticle;

import java.io.InputStream;

/**
 * 入口
 * Created by 7le on 2017/11/10
 */
public class ServiceMain {

    private static Vertx vertx;

    private static final String CONFIG_FILE = "ignite.xml";
    private static final String DEFAULT_CONFIG_FILE = "default-ignite.xml";

    private static void deploy(Vertx vertx) {
        System.out.println("Deploy Verticles");
        vertx.deployVerticle(new HttpServerVerticle());
        //vertx.deployVerticle(new ModelVerticle());
        //vertx.deployVerticle(new ModelVerticle());
    }

    public static void main(String args[]) {
        VertxOptions options = new VertxOptions()
                .setClustered(true)
                .setClusterManager(new IgniteClusterManager(loadConfiguration()));
        Vertx.clusteredVertx(options, vertxAsyncResult -> {
            if (vertxAsyncResult.succeeded()) {
                vertx = vertxAsyncResult.result();
                deploy(vertx);
            } else {
                System.out.println("Can't create cluster");
                System.exit(1);
            }
        });
    }

    private static IgniteConfiguration loadConfiguration() {
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();

        InputStream is = null;

        if (ctxClsLoader != null) {
            is = ctxClsLoader.getResourceAsStream(CONFIG_FILE);
        }
        if (is == null) {
            Class<ServiceMain> cls = ServiceMain.class;
            is = cls.getClassLoader().getResourceAsStream(CONFIG_FILE);
            if (is == null) {
                is = cls.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);
                System.out.println("Using default configuration.");
            }
        }
        try {
            return F.first(IgnitionEx.loadConfigurations(is).get1());
        } catch (IgniteCheckedException e) {
            System.out.println("Configuration loading error:"+e);
            throw new RuntimeException(e);
        }
    }
}
