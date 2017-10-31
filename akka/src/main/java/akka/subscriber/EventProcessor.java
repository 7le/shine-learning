package akka.subscriber;

import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理器 接受数据转换为最终的模型
 */
public class EventProcessor extends ClusterRoledWorker{

    /**
     * 内容的正则表达式
     */
    private static Pattern PATTERN = Pattern.compile("[\\?|&]([^=]+)=([^&]+)&");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterEvent.MemberUp.class, memberUp -> {
                    log.info("Member is Up: {}", memberUp.member().address());
                    register(memberUp.member(), getProcessorPath(memberUp.member()));
                })
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    Iterable<Member> members = state.getMembers();
                    // 如果加入Akka集群的成员节点是Up状态，并且是collector角色，则调用register向collector进行注册
                    members.forEach(o -> {
                        if (o.status() == MemberStatus.up())
                            register(o, getProcessorPath(o));
                    });
                })
                .match(ClusterEvent.UnreachableMember.class,o-> log.info("Member detected as unreachable: {}", o.member()))
                .match(ClusterEvent.MemberRemoved.class,o-> log.info("Member is Removed: {}", o.member()))
                .match(ClusterEvent.MemberEvent.class,o-> log.info("Member Event: {}", o.member()))
                .match(EventMessages.FilteredRecord.class, f ->{
                    //处理每一行日志内容,转换成Map模型
                    Map<String, String> data = process(f.getEventCode(), f.getLine(), f.getLogDate(), f.getRealIp());
                    log.info("Processed: data=" + data);
                })
                .build();

    }

    private Map<String, String> process(String eventCode, String line, String logDate, String realIp) {
        Map<String, String> data = new HashMap<>(16);

        Matcher matcher = PATTERN.matcher(line);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            data.put(key,value);
        }
        data.put("eventdate", logDate);
        data.put("realip", realIp);
        return data;
    }
    private String getProcessorPath(Member member) {
        return member.address()+"/user/interceptingActor";
    }
}
