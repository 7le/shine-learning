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
        while (!queue.isEmpty()){
            try {
                Message take=queue.poll();
                if(take!=null)
                System.out.println(Thread.currentThread().getName()+" 消费消息：" + take.getId() + ":" + take.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
