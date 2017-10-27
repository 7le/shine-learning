package akka.cluster.complete.fe;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ReceiveTimeout;
import akka.cluster.complete.be.Result;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.FromConfig;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * @author 7le
 * @Description: FE
 * @date 2017年10月27日
 * @since v1.0.0
 */
public class Frontend extends AbstractActor{

    final int upToN;        //计算到多少
    final boolean repeat;       //是否重复计算

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * 获取到Backend的Router
     */
    ActorRef backend = getContext().actorOf(FromConfig.getInstance().props(), "factorialBackendRouter");

    public Frontend(int upToN, boolean repeat) {
        this.upToN = upToN;
        this.repeat = repeat;
    }

    @Override
    public void preStart() {
        //因为是在Start前就发送消息,所以必定超时.
        sendJobs();
        getContext().setReceiveTimeout(Duration.create(10, TimeUnit.SECONDS));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Result.class,result -> {
                    System.out.println("计算的结果:" + result);
                    if (repeat)
                        sendJobs();
                    else
                        getContext().stop(getSelf());
                })
                .match(ReceiveTimeout.class, receiveTimeout -> {
                    log.info("超时");
                    sendJobs();
                })
                .matchAny(this::unhandled)
                .build();
    }

    private void sendJobs() {
        log.info("Starting batch of factorials up to [{}]", upToN);
        for (int n = 1; n <= upToN; n++) {
            backend.tell(n, getSelf());
        }
    }
}
