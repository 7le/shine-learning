package shine.http.demo1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class Server extends AbstractVerticle {

    private int number =0;

    public void start(Future<Void> fut) {
        Router router = Router.router(vertx);
        //单线程
        router.get("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/html")
                    .end("The number:"+number);
            number++;
        });

        // 将访问/assets/*的请求route到assets目录下的资源
        router.route("/assets/*").handler(StaticHandler.create("assets"));

        vertx.createHttpServer().requestHandler(router::accept)
                .listen(
                        config().getInteger("http.port", 8002),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                                System.out.println(8002);
                            } else {
                                fut.fail(result.cause());
                            }
                        });

    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Server());
    }
}
