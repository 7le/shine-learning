package akka.remote.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;

/**
 * @author hq
 * @Description: ClientActor发送给远端的服务端
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class ClientActor extends AbstractActor {

    private ActorSelection remoteServer = null;
    private long start;
    final static String EOF="EOF";

    public ClientActor(ActorSelection remoteServer) {
        this.remoteServer = remoteServer;
    }

    /**
     * 如果接收到的任务是String的,那么就直接发送给remoteServer
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, o -> {
                    if(EOF.equals(o)){
                        //这个的Sender设置为自己是为了接收聚合完成的消息
                        remoteServer.tell(o, getSelf());
                    }else {
                        remoteServer.tell(o, ActorRef.noSender());
                    }
                })
                .match(Boolean.class, o -> {
                    System.out.println("Congratulations,complete!");
                    remoteServer.tell("DISPLAY_LIST",ActorRef.noSender());
                    //执行完毕,关机
                    getContext().stop(self());
                })
                .build();
    }


    @Override
    public void preStart() {
        /*记录开始时间*/
        start = System.currentTimeMillis();
    }

    @Override
    public void postStop() {
        //计算用时
        long timeSpent = (System.currentTimeMillis() - start);
        System.out
                .println(String
                        .format("\n\tClientActor estimate: \t\t\n\tCalculation time: \t%s MS",
                                timeSpent));
    }
}
