package shine.map.local;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;

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
        RestAuth cache = new RestAuth();
        cache.setUsername("test");
        cache.setPassword("test");
        vertx.sharedData().getLocalMap("LocalMap").put("test", cache);

        LocalMap<String, RestAuth> apiMap = vertx.sharedData().getLocalMap("LocalMap");
        RestAuth result = apiMap.get("test");
        System.out.println(result.getUsername());
    }
}
