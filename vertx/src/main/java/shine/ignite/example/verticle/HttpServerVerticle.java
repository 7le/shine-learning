package shine.ignite.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import shine.ignite.example.constant.ServerConstant;

/**
 * 发送消息
 * Created by 7le on 2017/11/10
 */
public class HttpServerVerticle extends AbstractVerticle{

    private EventBus eventBus;

    @Override
    public void start() {
        eventBus = vertx.eventBus();
        Router router=Router.router(vertx);
        //设置跨域
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"));

        router.get("/").handler(routingContext ->{
            eventBus.publish(ServerConstant.NEWS,"publish");
            eventBus.send(ServerConstant.NEWS,"send",ar ->{
                if (ar.succeeded())
                    System.out.println("Received reply: " + ar.result().body());
                });
            routingContext.response().putHeader("content-type", "text/html")
                    .end("Message send");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8010);
    }

}
