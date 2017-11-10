package shine.ignite.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
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
        router.get("/").handler(routingContext ->{
            eventBus.publish(ServerConstant.NEWS,"publish");
            eventBus.send(ServerConstant.NEWS,"send");
            routingContext.response().putHeader("content-type", "text/html")
                    .end("Message send");
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8009);
    }

}
