package com.java.delayQueue;

import java.util.concurrent.DelayQueue;

/**
 * 消费者
 */
public class Consumer implements Runnable{

    //延时队列
    private DelayQueue<Message> queue;

    public Consumer(DelayQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                Message take=queue.take();
                System.out.println("消费消息：" + take.getId() + ":" + take.getBody());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
