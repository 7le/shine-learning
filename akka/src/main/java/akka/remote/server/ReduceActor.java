package akka.remote.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author 7le
 * @Description: MapAcotr计算把结果都发送给ReduceActor, 做汇总reduce计算
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class ReduceActor extends AbstractActor {

    static final String EOF = "EOF";

    /**
     * 管道Actor
     */
    private ActorRef actor = null;

    public ReduceActor(ActorRef inAggregateActor) {
        actor = inAggregateActor;
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("启动ReduceActor:" + Thread.currentThread().getName());
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(List.class, o -> {
                    try {
                        // 第一次汇总单词表结果.
                        NavigableMap<String, Integer> reducedList = reduce(o);
                        // 把这次汇总的结果发送给最终的结果聚合Actor
                        actor.tell(reducedList, ActorRef.noSender());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .match(Boolean.class, aBoolean -> {
                    //表示已经计算结束了,把这次汇总的结果发送给最终的结果聚合Actor
                    actor.tell(aBoolean, ActorRef.noSender());
                }).matchAny(o -> {
                    throw new IllegalArgumentException("Unknown message [" + o + "]");
                }).build();
    }

    /**
     * 聚合计算本次结果中各个单词的出现次数
     *
     * @param list
     * @return
     */
    private NavigableMap<String, Integer> reduce(List<Result> list) {

        NavigableMap<String, Integer> reducedMap = new ConcurrentSkipListMap<>();

        for (Result result : list) {
            //遍历结果,如果在这个小的结果中已经存在相同的单词了,那么数量+1,否则新建
            if (reducedMap.containsKey(result.getWord())) {
                Integer value = reducedMap.get(result.getWord());
                value++;
                reducedMap.put(result.getWord(), value);
            } else {
                reducedMap.put(result.getWord(), 1);
            }
        }
        return reducedMap;
    }
}
