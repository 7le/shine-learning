package com.java.delayQueue;

import org.testng.internal.thread.ThreadUtil;

import java.util.concurrent.*;

/**
 * 简易-任务调度
 */
public class ThreadPoolUtils {

    public static void main(String[] args) throws InterruptedException {
        //存放任务处理的队列
        DelayQueue<Message> delayQueue = new DelayQueue<Message>();

        ArrayBlockingQueue<Runnable> arrayBlockingQueue=new ArrayBlockingQueue<Runnable>(10);

        ThreadPoolExecutor threadPool =  new ThreadPoolExecutor(0, 20,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),   //直接提交
                //arrayBlockingQueue,           //有界队列
                //new LinkedBlockingDeque<>(),   //无界队列
                new ThreadUtil.ThreadFactoryImpl("pool-time-task-thread"));
        Consumer consumer=new Consumer(delayQueue);
        //输入数据
        Message m1 = new Message(1,"3S", 3000);
        delayQueue.offer(m1);


        for (int i = 1; i <= 1; i++) {
            threadPool.execute(consumer);
        }


        Message m2 = new Message(2,"500S", 500000);
        Message m3 = new Message(3,"30S", 30000);
        Message m4 = new Message(4,"1S", 1000);
        delayQueue.offer(m2);
        delayQueue.offer(m3);
        delayQueue.offer(m4);
/*
        for (int i = 0; i < 9999999 ; i++) {
            Message yo = new Message(10+i,"10S", 10000);
            delayQueue.offer(yo);
        }*/

        Message m5 = new Message(7,"7S", 7000);
        Message m6 = new Message(5,"14S", 14000);
        Message m7 = new Message(9,"5S", 5000);
        delayQueue.offer(m5);
        delayQueue.offer(m6);
        delayQueue.offer(m7);

        Thread.sleep(30000);
        System.out.println("again");
        delayQueue.offer(new Message(9,"again", 1000));
        consumer.wake();
        threadPool.shutdown();
/*        System.out.println("线程池需要执行的任务数量:"+threadPool.getTaskCount());
        System.out.println("线程池在运行过程中已完成的任务数量:"+threadPool.getCompletedTaskCount());
        System.out.println("线程池曾经创建过的最大线程数量:"+threadPool.getLargestPoolSize());
        System.out.println("线程池的线程数量:"+threadPool.getPoolSize());
        System.out.println("获取活动的线程数:"+threadPool.getActiveCount());*/
    }

}
