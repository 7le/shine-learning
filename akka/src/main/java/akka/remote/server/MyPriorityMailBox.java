package akka.remote.server;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.dispatch.PriorityGenerator;
import akka.dispatch.UnboundedPriorityMailbox;
import com.typesafe.config.Config;

/**
 * @author hq
 * @Description: 服务端通过MapReduceActor接受客户端发送的消息, 并将消息放入优先级MailBox
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class MyPriorityMailBox extends UnboundedPriorityMailbox {

    /**
     * 创建一个自定义优先级的无边界的邮箱. 用来规定命令的优先级. 这个就保证了DISPLAY_LIST 这个事件是最后再来处理.
     */
    public MyPriorityMailBox(ActorSystem.Settings settings, Config config) {

        // Creating a new PriorityGenerator,
        super(new PriorityGenerator() {
            @Override
            public int gen(Object message) {
                if (Constant.DISPLAY_LIST.equals(message))
                    return 2; // 'DisplayList messages should be treated
                    // last if possible
                else if (message.equals(PoisonPill.getInstance()))
                    return 3; // PoisonPill when no other left
                else
                    return 0; // By default they go with high priority
            }
        });
    }

}
