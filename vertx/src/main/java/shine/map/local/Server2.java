package shine.map.local;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.shareddata.LocalMap;

/**
 * @description: server2
 * @author : 7le
 * @date: 2017/11/22
 */
public class Server2 extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        System.out.println(getVertx().toString());
        LocalMap<String, RestAuth> apiMap1 = getVertx().sharedData().getLocalMap("LocalMap");
        RestAuth result1 = apiMap1.get("test");
        System.out.println("Server2: " + result1.getUsername());

    }
}
