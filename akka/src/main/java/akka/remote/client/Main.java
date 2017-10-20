package akka.remote.client;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * @author hq
 * @Description: Client
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class Main {

    public static void main(String[] args) throws Exception{
        //文件名
        final String fileName = "task.txt";

        //根据配置,找到System
        ActorSystem system=ActorSystem.create("ClientApplication", ConfigFactory.load("client")
            .getConfig("MapReduceClientApp"));

        //实例化远程Actor
        final ActorSelection remoteActor =system.actorSelection("akka.tcp://MapReduceApp@127.0.0.1:2552/user/MapReduceActor");

        //实例化Actor的管道
        final ActorRef fileReadActor = system.actorOf(Props.create(FileReadActor.class));

        final ActorRef clientActor = system.actorOf(Props.create(ClientActor.class, remoteActor.anchor()));

        //发送文件名给fileReadActor.设置sender或者说回调的Actor为clientActor
        fileReadActor.tell(fileName,clientActor);
    }
}
