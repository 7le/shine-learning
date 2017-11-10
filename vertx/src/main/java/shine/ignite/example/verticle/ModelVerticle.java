package shine.ignite.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import shine.ignite.example.constant.ServerConstant;


/**
 * 接受消息
 * Created by 7le on 2017/11/10
 */
public class ModelVerticle extends AbstractVerticle {

    private EventBus eventBus;

    @Override
    public void start() {
        eventBus = vertx.eventBus();
        System.out.println(Thread.currentThread());
        eventBus.consumer(ServerConstant.NEWS, message -> System.out.println("Model catch broadcast message: " + message.body()));
    }
}
