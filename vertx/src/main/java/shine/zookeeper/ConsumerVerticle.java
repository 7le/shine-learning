package shine.zookeeper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import shine.ignite.example.constant.ServerConstant;

/**
 * 接收消息
 *
 * @author 7le
 */
public class ConsumerVerticle extends AbstractVerticle{

    private EventBus eventBus;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        eventBus = vertx.eventBus();
        eventBus.consumer(ServerConstant.NEWS, message -> {
            System.out.println("Model catch broadcast message: " + message.body());
            message.reply("how interesting!");
        });
    }
}
