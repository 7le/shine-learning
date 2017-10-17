package akka.demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import sun.management.jmxremote.SingleEntryRegistry;

import java.util.concurrent.TimeUnit;

/**
 * @author hq
 * @Description: main
 * @date 2017年10月17日
 * @since v1.0.0
 */
public class Main {


    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("Singer");

        // 创建一个到singer Actor的管道
        final ActorRef singer = system.actorOf(Props.create(Singer.class), "singer");

        // 创建邮箱
        final Inbox inbox = Inbox.create(system);

        // 发送第一个消息
        singer.tell(new Microphone("Until you"), ActorRef.noSender());

        // 真正的发送消息,消息体为Greet
        inbox.send(singer,new Sing());

        // 等待5秒尝试接收Song返回的消息, 如果已经收到则立即返回，超时 ->TimeoutException
        Song song1 =(Song) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("Song: " + song1.song);

        // 发送第三个消息
        singer.tell(new Microphone("Proud of you"), ActorRef.noSender());

        // 发送第四个消息
        inbox.send(singer, new Sing());

        Song song2 = (Song) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("Song: " + song2.song);


        // 新创建一个Actor的管道
        ActorRef singerActor = system.actorOf(Props.create(NeteaseCloud.class));

        //使用schedule 每一秒发送一个Sing消息给 singerActor,然后把singerActor的消息返回给NeteaseCloud
        system.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS),
                singer, new Sing(), system.dispatcher(), singerActor);
        //system.shutdown();
    }
}
