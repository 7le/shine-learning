package shine.ignite.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import shine.ignite.example.constant.ServerConstant;

/**
 * 发送消息
 * Created by 7le on 2017/11/10
 */
public class HttpServerVerticle extends AbstractVerticle {

    private EventBus eventBus;

    private SharedData sd;

    @Override
    public void start() {
        eventBus = vertx.eventBus();
        sd = vertx.sharedData();

        sd.<String, String>getClusterWideMap("mymap", res -> {
            if (res.succeeded()) {
                AsyncMap<String, String> map = res.result();
                map.put("foo", "bar", resPut -> {
                    if (resPut.succeeded()) {
                        System.out.println("HttpServerVerticle map put success");
                    } else {
                        System.out.println("HttpServerVerticle map put fail");
                    }
                });
            } else {
                System.out.println("HttpServerVerticle mymap get fail");
            }
        });


        Router router = Router.router(vertx);
        //设置跨域
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"));

        router.get("/").handler(routingContext -> {
            eventBus.publish(ServerConstant.NEWS, "publish");
            eventBus.send(ServerConstant.NEWS, "send", ar -> {
                if (ar.succeeded())
                    System.out.println("Received reply: " + ar.result().body());
            });
            routingContext.response().putHeader("content-type", "text/html")
                    .end("Message send");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8010);
    }

}
