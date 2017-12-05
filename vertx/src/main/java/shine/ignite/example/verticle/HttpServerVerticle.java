package shine.ignite.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import shine.ignite.example.constant.ServerConstant;
import shine.spring.dao.model.Video;
import shine.spring.util.IgniteCacheUtils;

import java.util.List;

/**
 * @description: 发送消息
 * @author : 7le
 * @date: 2017/11/10
 */
public class HttpServerVerticle extends AbstractVerticle {

    private EventBus eventBus;
    private SharedData sd;

    @Override
    public void start() {
        eventBus = vertx.eventBus();
        sd = vertx.sharedData();

        //设置本地缓存
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

        router.get("/").handler(this::send);

        router.get("/ignite").handler(this::cache);

        router.get("/ignite/sql").handler(this::sql);


        vertx.createHttpServer().requestHandler(router::accept).listen(8010);
    }

    private void send(RoutingContext routingContext){
        eventBus.publish(ServerConstant.NEWS, "publish");
        eventBus.send(ServerConstant.NEWS, "send", ar -> {
            if (ar.succeeded())
                System.out.println("Received reply: " + ar.result().body());
        });
        routingContext.response().putHeader("content-type", "text/html")
                .end("Message send");
    }

    private void cache(RoutingContext routingContext){
        //设置集群缓存
        IgniteCacheUtils.putCache("cluster", "Do you receive me ?");
        System.out.println("Add ignite cache !");
        eventBus.send(ServerConstant.NEWS, "send", ar -> {
            if (ar.succeeded())
                System.out.println("Received reply: " + ar.result().body());
        });
        routingContext.response().putHeader("content-type", "text/html")
                .end("Message send");
    }

    private void sql(RoutingContext routingContext){
        vertx.executeBlocking(s->{
            IgniteCache cache = IgniteCacheUtils.getVideo();
            cache.loadCache(null);
            Video video = (Video) cache.get(22);
            System.out.println("vid :22 name: " + video.getName());
            QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery("select * from Video"));
            System.out.println(cursor.getAll());
            SqlQuery sql = new SqlQuery(Video.class, "vid > ?");
            System.out.println(cache.query(sql.setArgs(30)).getAll());
            s.complete();
        },result ->routingContext.response().putHeader("content-type", "text/html")
                .end("OK"));
    }
}
