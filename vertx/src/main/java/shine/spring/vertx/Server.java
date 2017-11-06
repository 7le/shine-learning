package shine.spring.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import shine.spring.dao.model.Video;
import shine.spring.service.VideoService;
import shine.spring.util.SpringUtils;

import java.util.List;

/**
 * 使用WorkerPool 切记不能阻塞event loop
 */
public class Server extends AbstractVerticle {

    private static Logger log = LoggerFactory.getLogger(Server.class);

    private VideoService videoService= SpringUtils.getBean(VideoService.class);

    public static void main(String[] args) {
        deploy();
    }

    public static void deploy() {
        Vertx.vertx().deployVerticle(Server.class.getName());
    }


    @Override
    public void start(Future<Void> fut) throws Exception {
        // Create a router object.
        Router router = Router.router(vertx);

        // Rest of the method
        router.route("/video*").handler(BodyHandler.create());
        //router.post("/video").handler(this::addOne);
        router.get("/video").handler(this::getAll);
        router.get("/video/:id").handler(this::getOne);

        vertx.createHttpServer().requestHandler(router::accept)
                .listen(
                        config().getInteger("http.port", 8005),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                                System.out.println(8005);
                            } else {
                                fut.fail(result.cause());
                            }
                        });
    }

    private void getOne(RoutingContext routingContext) {
        final String[] video = new String[1];
        String id = routingContext.request().getParam("id");
        //调用worker pool 就不会阻塞event loop
        //缺省true为串行 false为并行
        vertx.executeBlocking(future -> {
            try {
                video[0] = videoService.selectOne(Integer.valueOf(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
            future.complete(1);
        }, true, asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println("查询完毕 " + asyncResult);
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(video[0]);
            }
        });

    }

    private void getAll(RoutingContext routingContext) {
        final String[] video = new String[1];
        vertx.executeBlocking(future -> {
            try {
                video[0] = videoService.page(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            future.complete(1);
        }, true, asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println("查询完毕 " + asyncResult);
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(video[0]);
            }
        });
    }

}
