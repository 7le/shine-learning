package akka.demo;

import akka.actor.AbstractActor;

/**
 * @author hq
 * @Description: 歌手
 * @date 2017年10月17日
 * @since v1.0.0
 */
public class Singer extends AbstractActor {
    String song = "";

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Microphone.class,s -> song = "hello, I will Singing " + s.who)
                .match(Sing.class,o -> getSender().tell(new Song(song), getSelf()))
                .build();
    }
}
