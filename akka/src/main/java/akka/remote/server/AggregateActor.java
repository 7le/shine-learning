package akka.remote.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 7le
 * @Description: 如果是DISPLAY_LIST标识, 那么就打印结果.如果是Boolean就表示统计完成了
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class AggregateActor extends AbstractActor {

    /**
     * 结果
     */
    private Map<String, Integer> finalReducedMap = new HashMap<>();

    @Override
    public void preStart() throws Exception {
        System.out.println("启动AggregateActor:" + Thread.currentThread().getName());
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Map.class, map -> aggregateInMemoryReduce(map))
                .match(String.class, s -> {
                    if (Constant.DISPLAY_LIST.equals(s))
                        System.out.println(finalReducedMap.toString());
                })
                .match(Boolean.class, aBoolean -> getSender().tell(true, ActorRef.noSender()))
                .build();
    }

    private void aggregateInMemoryReduce(Map<String, Integer> reducedList) {

        for (String key : reducedList.keySet()) {
            //最终的数量的累加
            if (finalReducedMap.containsKey(key)) {
                Integer count = reducedList.get(key) + finalReducedMap.get(key);
                finalReducedMap.put(key, count);
            } else {
                finalReducedMap.put(key, reducedList.get(key));
            }

        }
    }
}
