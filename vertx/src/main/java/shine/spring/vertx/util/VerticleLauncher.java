package shine.spring.vertx.util;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.spi.cluster.ignite.IgniteClusterManager;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.IgnitionEx;
import org.apache.ignite.internal.util.typedef.F;
import shine.spring.util.IpUtils;

import java.io.InputStream;
import java.util.function.Consumer;

/**
 * VerticleLauncher
 * Created by 7le on 2017/11/16
 */
public class VerticleLauncher {

    private static Vertx vertx;

    private static final String CONFIG_FILE = "ignite.xml";
    private static final String DEFAULT_CONFIG_FILE = "default-ignite.xml";

    public static void deployVerticle(VertxOptions options, Consumer<Vertx> runner) {
        options.setClusterManager(new IgniteClusterManager(loadConfiguration()));
        Vertx.clusteredVertx(options, vertxAsyncResult -> {
            if (vertxAsyncResult.succeeded()) {
                vertx = vertxAsyncResult.result();
                runner.accept(vertx);
            } else {
                System.out.println("Can't create cluster");
                System.exit(1);
            }
        });
    }

    public static void deployVerticleWithDefaultConfig(Class<? extends Verticle> verticle, boolean worker) {
        deployVerticleWithDefaultConfig(verticle.getName(),worker);
    }

    public static void deployVerticleWithDefaultConfig(String verticle, boolean worker) {
        String ip = IpUtils.getIpAddress();
        VertxOptions options = new VertxOptions().setClustered(true).setClusterHost(ip).setWorkerPoolSize(100).setClusterHost(ip);
        EventBusOptions eventBusOptions = options.getEventBusOptions();
        if(eventBusOptions == null){
            eventBusOptions =new EventBusOptions();
        }
        options.setEventBusOptions(eventBusOptions.setReconnectAttempts(50).setClusterPingInterval(5678).setHost(ip));
        DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(worker);
        VerticleLauncher.deployVerticle(verticle,options,deploymentOptions,null);
    }

    public static void deployVerticle(String verticle, VertxOptions options, DeploymentOptions deploymentOptions, Handler<AsyncResult<String>> completionHandler) {
        if (options == null) {
            options = new VertxOptions();
        }
        Consumer<Vertx> runner = vertx -> {
            Handler<AsyncResult<String>> h = completionHandler;
            if( h==null){
                h = a->{};
            }
            try {
                if (deploymentOptions != null) {
                    vertx.deployVerticle(verticle, deploymentOptions,h);
                } else {
                    vertx.deployVerticle(verticle,h);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        if (options.isClustered()) {
            deployVerticle(options, runner);
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
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
}

