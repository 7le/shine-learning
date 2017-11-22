package shine.map.local;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 * 入口
 * Created by 7le on 2017/11/22
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(Server.class.getName());
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        //需要在同一实例下 localMap
/*        Vertx.vertx().deployVerticle(Server1.class.getName());
        Thread.sleep(1000);
        Vertx.vertx().deployVerticle(Server2.class.getName());*/

        getVertx().deployVerticle(Server1.class.getName());
        Thread.sleep(1000);
        getVertx().deployVerticle(Server2.class.getName());
    }
}
