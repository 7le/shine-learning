package shine.spring.vertx.util;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.ignite.IgniteClusterManager;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.IgnitionEx;
import org.apache.ignite.internal.util.typedef.F;
import shine.spring.vertx.HttpVerticle;

import java.io.InputStream;

/**
 * VerticleLauncher
 * Created by 7le on 2017/11/16
 */
public class VerticleLauncher {

    private static Vertx vertx;

    private static final String CONFIG_FILE = "ignite.xml";
    private static final String DEFAULT_CONFIG_FILE = "default-ignite.xml";

    public static void deployVerticle() {
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
            Class<VerticleLauncher> cls = VerticleLauncher.class;
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

    private static void deploy(Vertx vertx) {
        vertx.deployVerticle(HttpVerticle.class.getName());
    }
}
