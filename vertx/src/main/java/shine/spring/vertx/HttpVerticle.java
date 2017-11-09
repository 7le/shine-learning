package shine.spring.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * HttpVerticle
 * Created by 7le on 2017/11/6
 */
public class HttpVerticle extends AbstractVerticle{

    static public void deploy(){
        Vertx.vertx().deployVerticle(new HttpVerticle());
    }

    @Override
    public void start() throws Exception {
        getVertx().deployVerticle(VideoVerticle.class.getName());
        //可以继续加入Verticle
    }
}
