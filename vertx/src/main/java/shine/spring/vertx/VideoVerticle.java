package shine.spring.vertx;

import com.alibaba.fastjson.JSON;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shine.spring.annotation.EventBusService;
import shine.spring.bean.MonitorInfo;
import shine.spring.dao.model.Video;
import shine.spring.service.VideoService;
import shine.spring.util.SpringUtils;

/**
 * @description: 使用WorkerPool 切记不能阻塞event loop
 * @author : 7le
 * @date: 2017/11/6
 */
public class VideoVerticle extends AbstractVerticle {

    private static Logger log = LoggerFactory.getLogger(VideoVerticle.class);

    private VideoService videoService = SpringUtils.getBean(VideoService.class);

    private EventBusService eventBusService = SpringUtils.getBean(EventBusService.class);

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(VideoVerticle.class.getName());
    }

    @Override
    public void start(Future<Void> fut) throws Exception {
        // Create a router object.
        Router router = Router.router(vertx);

        //设置跨域
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-PINGARUNER")
                .allowedHeader("Content-Type"));
        // Rest of the method
        router.get("/server/status").handler(this::getStatus);
        router.get("/server/monitor").handler(this::getMonitor);
        router.route("/video*").handler(BodyHandler.create());
        router.post("/video").handler(this::addOne);
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

    /**
     * 利用eventbus
     * @param routingContext
     */
    private void addOne(RoutingContext routingContext){
        final Video video = Json.decodeValue(routingContext.getBodyAsString(),
                Video.class);
        eventBusService.postEvent(video);
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end();
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
            future.complete(video[0]);
        }, false, asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println("查询完毕 " + asyncResult);
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(asyncResult.result().toString());
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
            future.complete(video[0]);
        }, false, asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println("查询完毕 " + asyncResult);
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(asyncResult.result().toString());
            }
        });
    }

    private void getStatus(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "text/plain")
                .end("Hello!");
    }

    private void getMonitor(RoutingContext routingContext) {
        MonitorInfo info = new MonitorInfo();
        // 剩余内存
        info.setFreeMemory(Runtime.getRuntime().freeMemory());
        // 可使用内存
        info.setTotalMemory(Runtime.getRuntime().totalMemory());
        // 最大可使用内存
        info.setMaxMemory(Runtime.getRuntime().maxMemory());
        // 线程总数
        ThreadGroup tg;
        for (tg = Thread.currentThread().getThreadGroup(); tg.getParent() != null; tg = tg.getParent());
        info.setTotalThread(tg.activeCount());
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(JSON.toJSONString(info));
    }

}
