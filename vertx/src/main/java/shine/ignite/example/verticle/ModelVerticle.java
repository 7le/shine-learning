package shine.ignite.example.verticle;

import com.google.common.base.Strings;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import shine.ignite.example.constant.ServerConstant;
import shine.spring.dao.model.Video;
import shine.spring.util.IgniteCacheUtils;

import java.util.List;

/**
 * @description: 接受消息
 * @author : 7le
 * @date: 2017/11/10
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
            String cache = IgniteCacheUtils.getCache("cluster");

            IgniteCache cache1 = IgniteCacheUtils.getVideo();
            Video video = (Video) cache1.get(22);
            System.out.println("vid :22 name: " + video.getName());
            QueryCursor<List<?>> cursor = cache1.query(new SqlFieldsQuery("select * from Video"));
            System.out.println(cursor.getAll());
            if(Strings.isNullOrEmpty(cache)){
                System.out.println("Sorry ,I don't ");
            } else {
                System.out.println(cache);
                System.out.println("Of course!");
            }
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
