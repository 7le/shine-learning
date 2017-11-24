package shine.ignite.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;
import shine.ignite.example.constant.ServerConstant;


/**
 * 接受消息
 * Created by 7le on 2017/11/10
 */
public class ModelVerticle extends AbstractVerticle {

    private EventBus eventBus;

    private SharedData sd;

    @Override
    public void start() {
        eventBus = vertx.eventBus();
        sd = vertx.sharedData();
        System.out.println(Thread.currentThread().getName());
        eventBus.consumer(ServerConstant.NEWS, message -> {
            System.out.println("Model catch broadcast message: " + message.body());
            message.reply("how interesting!");

            sd.<String, String>getClusterWideMap("mymap", res -> {
                if (res.succeeded()) {
                    AsyncMap<String, String> map = res.result();
                    map.get("foo", resGet -> {
                        if (resGet.succeeded()) {
                            // Successfully got the value
                            Object val = resGet.result();
                            System.out.println("ModelVerticle map get success :" +val);
                        } else {
                            System.out.println("ModelVerticle map get fail");
                        }
                    });
                } else {
                    System.out.println("ModelVerticle mymap get fail");
                }
            });

        });
    }
}
