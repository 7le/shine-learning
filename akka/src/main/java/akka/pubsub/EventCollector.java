package akka.pubsub;

import akka.actor.Terminated;
import akka.cluster.ClusterEvent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 收集器: 接收数据,然后将数据发送到interceptor
 */
public class EventCollector extends ClusterRoledWorker{

    private AtomicInteger recordCounter = new AtomicInteger(0);

    private static Pattern pattern = Pattern.compile("eventcode=(\\d+)");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterEvent.MemberUp.class, o -> log.info("Member is Up:{}",o.member()))
                .match(ClusterEvent.UnreachableMember.class,o-> log.info("Member detected as unreachable: {}", o.member()))
                .match(ClusterEvent.MemberRemoved.class,o-> log.info("Member is Removed: {}", o.member()))
                .match(ClusterEvent.MemberEvent.class,o-> log.info("Member Event: {}", o.member()))
                .match(EventMessages.Registration.class, registration -> {
                    // watch发送注册消息的interceptor，如果对应的Actor终止了，会发送一个Terminated消息
                    getContext().watch(getSender());
                    workers.add(getSender());
                    log.info("Interceptor registered: " + getSender());
                    log.info("Registered interceptors: " + workers.size());
                })
                .match(Terminated.class, terminate -> workers.remove(terminate.actor()))
                .match(EventMessages.RawNginxRecord.class, rawNginxRecord -> {
                    String line = rawNginxRecord.getLine();
                    String sourceHost = rawNginxRecord.getSourceHost();
                    String eventCode = findEventCode(line);
                    // 构造NginxRecord消息，发送到interceptor
                    log.info("Raw message: eventCode=" + eventCode + ", sourceHost=" + sourceHost + ", line=" + line);
                    int counter = recordCounter.incrementAndGet();
                    if (workers.size() > 0) {
                        // 模拟round-robin方式，将日志记录消息发送给下游一组interceptor中的一个
                        int interceptorIndex = (counter < 0 ? 0 : counter) % workers.size();
                        workers.get(interceptorIndex).tell(new EventMessages.NginxRecord(sourceHost, line, eventCode), getSelf());
                        log.info("Details: interceptorIndex=" + interceptorIndex + ", interceptors=" + workers.size());
                    }
                })
                .build();
    }

    private String findEventCode(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
