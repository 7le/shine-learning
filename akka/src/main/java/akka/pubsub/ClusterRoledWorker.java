package akka.pubsub;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅集群事件
 */
public class ClusterRoledWorker extends AbstractActor {

    /**
     * 日志
     */
    protected LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * 集群
     */
    protected Cluster cluster = Cluster.get(getContext().system());

    /**
     * 缓存ActorRef
     */
    protected List<ActorRef> workers = new ArrayList<>();

    @Override
    public void preStart() throws Exception {
        //订阅集群事件
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);
    }

    @Override
    public Receive createReceive() {
        return null;
    }

    @Override
    public void postStop() throws Exception {
        //取消事件监听
        cluster.unsubscribe(getSelf());
    }

    /**
     * 子系统节点发送注册消息
     */
    protected void register(Member member, String actorPath) {
        ActorSelection actorSelection = getContext().actorSelection(actorPath);

        //发送注册消息
        actorSelection.tell(new EventMessages.Registration(), getSelf());
    }
}
