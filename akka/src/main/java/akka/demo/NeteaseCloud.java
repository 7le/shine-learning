package akka.demo;

import akka.actor.AbstractActor;

/**
 * @author 7le
 * @Description: 接受song，并可以循环播放
 * @date 2017年10月17日
 * @since v1.0.0
 */
public class NeteaseCloud extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Song.class, s -> System.out.println(s.song))
                .build();
    }
}