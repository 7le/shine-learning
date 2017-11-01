package akka.pubsub;

import akka.actor.Terminated;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拦截器: 接收到数据后,解析出真实IP地址,拦截存在于黑名单中的IP请求,
 * 如果IP地址不在黑名单,则发送给processor
 */
public class EventInterceptor extends ClusterRoledWorker {

    private AtomicInteger interceptedRecords = new AtomicInteger(0);

    /**
     * IP地址的正则表达式
     */
    private static Pattern IP_PATTERN = Pattern.compile("[^\\s]+\\s+\\[([^\\]]+)\\].+\"(\\d+\\.\\d+\\.\\d+\\.\\d+)");

    /**
     * 黑名单
     */
    private List<String> blackIpList = Arrays.asList("104.200.31.32", "104.200.31.238", "123.182.129.108",
            "220.161.98.39", "59.58.152.90", "117.26.221.236", "59.58.150.110", "123.180.229.156");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterEvent.MemberUp.class, memberUp -> {
                    log.info("Member is Up: {}", memberUp.member().address());
                    register(memberUp.member(), getCollectorPath(memberUp.member()));
                })
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    Iterable<Member> members = state.getMembers();
                    // 如果加入Akka集群的成员节点是Up状态，并且是collector角色，则调用register向collector进行注册
                    members.forEach(o -> {
                        if (o.status() == MemberStatus.up())
                            register(o, getCollectorPath(o));
                    });
                })
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
                .match(EventMessages.NginxRecord.class, nginxRecord -> {
                    CheckRecord checkRecord = checkRecord(nginxRecord.getEventCode(), nginxRecord.getLine());

                    if (!checkRecord.isIpInBlackList){
                        int records = interceptedRecords.incrementAndGet();

                        if (workers.size()>0){
                            int processorIndex = (records<0?0:records) % workers.size();
                            workers.get(processorIndex).tell(new EventMessages.FilteredRecord(nginxRecord.getSourceHost(),nginxRecord.getLine(), nginxRecord.getEventCode() , checkRecord.data.get("eventdate"), checkRecord.data.get("realip")),getSelf());
                            log.info("Details: processorIndex=" + processorIndex + ", processors=" + workers.size());
                        }
                        log.info("Intercepted data: data=" + checkRecord.data);
                    }else {
                        log.info("Discarded: " + nginxRecord.getLine());
                    }
                })
                .build();

    }

    /**
     * 获取Collector的路径
     */
    private String getCollectorPath(Member member) {
        return member.address() + "/user/collectingActor";
    }

    /**
     * 检查和解析每一行日志的记录
     */
    private CheckRecord checkRecord(String eventCode,String line){

        Map<String,String> data = new HashMap<>();
        boolean isIpInBlackList = false;
        Matcher matcher = IP_PATTERN.matcher(line);
        while (matcher.find()) {
            String rawDt = matcher.group(1);
            String realIp = matcher.group(2);
            data.put("eventdate", rawDt);
            data.put("realip", realIp);
            data.put("eventcode", eventCode);

            isIpInBlackList = blackIpList.contains(realIp);
        }

        return new CheckRecord(isIpInBlackList,data);
    }

    private class CheckRecord {
        private boolean isIpInBlackList;

        private Map<String, String> data;

        public CheckRecord(boolean isIpInBlackList, Map<String, String> data) {
            this.isIpInBlackList = isIpInBlackList;
            this.data = data;
        }
    }
}
