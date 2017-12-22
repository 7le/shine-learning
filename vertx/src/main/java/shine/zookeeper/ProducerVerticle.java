package shine.zookeeper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import shine.ignite.example.constant.ServerConstant;

/**
 * 发送消息
 *
 * @author 7le
 */
public class ProducerVerticle extends AbstractVerticle {

    private EventBus eventBus;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        eventBus = getVertx().eventBus();
        Router router = Router.router(vertx);
        //设置跨域
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"));

        router.get("/").handler(this::send);

        vertx.createHttpServer().requestHandler(router::accept).listen(8000);
    }

    private void send(RoutingContext routingContext) {
        eventBus.publish(ServerConstant.NEWS, "publish");
        eventBus.send(ServerConstant.NEWS, "send", ar -> {
            if (ar.succeeded())
                System.out.println("Received reply: " + ar.result().body());
        });
        routingContext.response().putHeader("content-type", "text/html")
                .end("Message send");
    }
}
