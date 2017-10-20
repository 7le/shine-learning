package akka.remote.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

/**
 * @author hq
 * @Description: 消息中转和统管的Actor, 它统管了其他的几个Actor, 是消息的入口
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class MapReduceActor extends AbstractActor {

    private ActorRef mapRouter;
    private ActorRef aggregateActor;

    @Override
    public void preStart() throws Exception {
        System.out.println("启动MapReduceActor:" + Thread.currentThread().getName());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.compareTo(Constant.DISPLAY_LIST) == 0) {
                        System.out.println("Got Display Message");
                        aggregateActor.tell(s, getSender());
                    }
                    if (Constant.EOF.equals(s)) {
                        aggregateActor.tell(true, getSender());
                    } else {
                        //否则给map的Actor进行计算
                        mapRouter.tell(s, ActorRef.noSender());
                    }
                }).build();
    }

    public MapReduceActor(ActorRef inAggregateActor, ActorRef inMapRouter) {
        mapRouter = inMapRouter;
        aggregateActor = inAggregateActor;
    }
}
