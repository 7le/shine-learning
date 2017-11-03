package shine.http.demo3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 使用WorkerPool 切记不能阻塞event loop
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Server());
    }

    // Store our product
    private Map<Integer, Whisky> products = new LinkedHashMap<>();

    /**
     * Create some product
     */
    private void createSomeData() {
        Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
        products.put(bowmore.getId(), bowmore);
        Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
        products.put(talisker.getId(), talisker);
    }

    @Override
    public void start(Future<Void> fut) throws Exception {
        createSomeData();

        // Create a router object.
        Router router = Router.router(vertx);

        // Rest of the method
        router.get("/api/whiskies").handler(this::getAll);
        router.get("/api/whiskies2").handler(this::getAll2);

        vertx.createHttpServer().requestHandler(router::accept)
                .listen(
                        config().getInteger("http.port", 8004),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                                System.out.println(8004);
                            } else {
                                fut.fail(result.cause());
                            }
                        });
    }

    private void getAll(RoutingContext routingContext) {
        //调用worker pool 就不会阻塞event loop
        //缺省true为串行 false为并行
        vertx.executeBlocking(future -> {
            try {
                //模拟查询db
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete(1);
        }, true, asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println("查询完毕 " + asyncResult);
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(products.values()));
            }
        });

    }

    private void getAll2(RoutingContext routingContext) {
        try {
            //阻塞event loop线程 会出警告
            //警告: Thread Thread[vert.x-eventloop-thread-0,5,main] has been blocked for 2917 ms, time limit is 2000
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(products.values()));
    }
}
