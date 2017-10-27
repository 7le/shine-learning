package akka.remote.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 7le
 * @Description: FileReadActor从文本文件中读取文件
 * @date 2017年10月20日
 * @since v1.0.0
 */
public class FileReadActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, o -> {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(Thread.currentThread().getContextClassLoader().getResource(o).openStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                             //遍历,一行一个消息反馈给消息发送方
                            getSender().tell(line, ActorRef.noSender());
                        }
                        System.out.println("End ...");
                        //发送一个结束标识
                        getSender().tell(String.valueOf("EOF"),ActorRef.noSender());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .matchAny(o -> {
                    throw new IllegalArgumentException("Unknown message [" + o + "]");
                })
                .build();
    }
}
