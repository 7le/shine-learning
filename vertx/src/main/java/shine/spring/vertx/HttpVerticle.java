package shine.spring.vertx;

import io.vertx.core.AbstractVerticle;
import shine.spring.vertx.util.VerticleLauncher;

/**
 * HttpVerticle
 * Created by 7le on 2017/11/6
 */
public class HttpVerticle extends AbstractVerticle{

    static public void deploy(){
        VerticleLauncher.deployVerticleWithDefaultConfig(HttpVerticle.class,true);
    }

    @Override
    public void start() throws Exception {
        getVertx().deployVerticle(VideoVerticle.class.getName());
        //可以继续加入Verticle
    }
}
