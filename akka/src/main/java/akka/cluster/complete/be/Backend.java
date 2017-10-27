package akka.cluster.complete.be;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.dispatch.Mapper;
import scala.concurrent.Future;

import java.math.BigInteger;

import static akka.dispatch.Futures.future;
import static akka.pattern.Patterns.pipe;


/**
 * @author 7le
 * @Description: BE
 * @date 2017年10月27日
 * @since v1.0.0
 */
public class Backend extends AbstractActor{

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class, integer -> {
                    //使用akka的future功能,异步的计算阶乘
                    Future<BigInteger> f=future(() -> factorial(integer), getContext().dispatcher());
                    //合并计算的结果
                    Future<Result> r=f.map(new Mapper<BigInteger, Result>() {
                        @Override
                        public Result apply(BigInteger factorial) {
                            return new Result(integer, factorial);
                        }
                    }, getContext().dispatcher());
                    //把结果返回Sender
                    pipe(r, getContext().dispatcher()).to(getSender());
                })
                .matchAny(this::unhandled)
                .build();
    }

    /**
     * 进行阶乘计算
     * @param n
     * @return
     */
    BigInteger factorial(int n) {
        BigInteger acc = BigInteger.ONE;
        for (int i = 1; i <= n; ++i) {
            acc = acc.multiply(BigInteger.valueOf(i));
        }
        return acc;
    }
}
