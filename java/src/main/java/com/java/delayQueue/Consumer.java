package com.java.delayQueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * 消费者
 */
public class Consumer implements Runnable{

    private final Object sigLock = new Object();

    //延时队列
    private DelayQueue<Message> queue;

    public Consumer(DelayQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()){
            synchronized (sigLock) {
                try {
                    System.out.println(System.currentTimeMillis());
                    Message take = queue.poll();
                    Message ab = queue.peek();
                    if (take != null) {
                        System.out.println(Thread.currentThread().getName() + " 消费消息：" + take.getId() + ":" + take.getBody());
                    } else {
                        try {
                            Message next = queue.peek();
                            if (next != null) {
                                long time = TimeUnit.MILLISECONDS.convert(next.getExcuteTime() - System.nanoTime(), TimeUnit.NANOSECONDS);
                                System.out.println("下一次等待多少ms:" + time);
                                sigLock.wait(time + 1000L);
                            } else {
                                sigLock.wait(99999999);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void wake() {
        synchronized (sigLock) {
            sigLock.notifyAll();
        }
    }
}
