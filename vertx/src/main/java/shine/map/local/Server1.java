package shine.map.local;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.shareddata.LocalMap;

/**
 * @description: server1
 * @author : 7le
 * @date: 2017/11/22
 */
public class Server1 extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        System.out.println(getVertx().toString());
        RestAuth cache = new RestAuth();
        cache.setUsername("test");
        cache.setPassword("test");
        getVertx().sharedData().getLocalMap("LocalMap").put("test", cache);

        LocalMap<String, RestAuth> apiMap = getVertx().sharedData().getLocalMap("LocalMap");
        RestAuth result = apiMap.get("test");
        System.out.println("Server1: " + result.getUsername());

        RestAuth cache1 = new RestAuth();
        cache1.setUsername("test1");
        cache1.setPassword("test1");
        getVertx().sharedData().getLocalMap("LocalMap").put("test", cache1);

        LocalMap<String, RestAuth> apiMap1 = getVertx().sharedData().getLocalMap("LocalMap");
        RestAuth result1 = apiMap1.get("test");
        System.out.println("Server1: " + result1.getUsername());
    }
}
