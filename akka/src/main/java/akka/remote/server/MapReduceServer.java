package akka.remote.server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.typesafe.config.ConfigFactory;

/**
 * @author hq
 * @Description: 服务端的入口程序,定义了任意数量actor的单词统计服务.并使用轮询模式来分发客服端接收到的统计任务.
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class MapReduceServer {

    private ActorRef mapRouter;
    private ActorRef reduceRouter;
    private ActorRef aggregateActor;
    private ActorRef mapReduceActor;

    public MapReduceServer(int no_of_reduce_workers, int no_of_map_workers) {

        //创建了Actor系统
        ActorSystem system = ActorSystem.create("MapReduceApp", ConfigFactory.load("server")
                .getConfig("MapReduceApp"));

        // 创建聚合Actor
        aggregateActor = system.actorOf(Props.create(AggregateActor.class));

        // 创建多个聚合的Actor
        reduceRouter = system.actorOf(Props.create(ReduceActor.class,aggregateActor).withRouter(new RoundRobinPool(no_of_reduce_workers)));

        // 创建多个Map的Actor
        mapRouter = system.actorOf(Props.create(MapActor.class,reduceRouter).withRouter(new RoundRobinPool(no_of_map_workers)));

        // 创建 MapReduce Actor 远程Actor
        mapReduceActor = system.actorOf(Props.create(MapReduceActor.class,aggregateActor,mapRouter)
                .withDispatcher("priorityMailBox-dispatcher"), "MapReduceActor");
    }

    public static void main(String[] args) {
        new MapReduceServer(5,5);
    }
}
