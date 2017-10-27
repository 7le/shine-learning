package akka.cluster.simple;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author 7le
 * @Description: 监听
 * @date 2017年10月27日
 * @since v1.0.0
 */
public class ClusterListener extends AbstractActor{

    /**
     * 记录日志
     */
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * 创建,获取集群
     */
    Cluster cluster = Cluster.get(getContext().system());

    /**
     * 六种状态：
     * ClusterEvent.MemberJoined - A new member has joined the cluster and its status has been changed to Joining
     * ClusterEvent.MemberUp - A new member has joined the cluster and its status has been changed to Up
     * ClusterEvent.MemberExited - A member is leaving the cluster and its status has been changed to Exiting Note that the node might already have been shutdown when this event is published on another node.
     * ClusterEvent.MemberRemoved - Member completely removed from the cluster.
     * ClusterEvent.UnreachableMember - A member is considered as unreachable, detected by the failure detector of at least one other node.
     * ClusterEvent.ReachableMember - A member is considered as reachable again, after having been unreachable. All nodes that previously detected it as unreachable has detected it as reachable again.
     * 状态说明：
     * Joining: 加入集群的瞬间状态
     * Up: 正常服务状态
     * Leaving / Exiting: 正常移出中状态
     * Down: 被标记为停机（不再是集群决策的一部分）
     * Removed: 已从集群中移除
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClusterEvent.MemberUp.class, o -> log.info("Member is Up:{}",o.member()))
                .match(ClusterEvent.UnreachableMember.class,o-> log.info("Member detected as unreachable: {}", o.member()))
                .match(ClusterEvent.MemberRemoved.class,o-> log.info("Member is Removed: {}", o.member()))
                .match(ClusterEvent.MemberEvent.class,o-> log.info("Member Event: {}", o.member()))
                .build();
    }

    /**
     * 订阅集群中的事件
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception {
        //region subscribe
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), ClusterEvent.MemberEvent.class,
                ClusterEvent.UnreachableMember.class);
        //end region
    }

    //re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }
}
