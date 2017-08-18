package com.java.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService > timer
 * Timer在执行定时任务时只会创建一个线程
 * Created by 7le on 2017/8/18 .
 */
public class SchedulerExecutorDemo implements Runnable {
    private final String jobName;

    public SchedulerExecutorDemo(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println("running => " + jobName);
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        // 一秒 周期 是以上一个任务开始的时间计时
        executor.scheduleAtFixedRate(new SchedulerExecutorDemo("job1"), 1, 1, TimeUnit.SECONDS);

        // 两秒 周期 以上一个任务结束时开始计时
        executor.scheduleWithFixedDelay(new SchedulerExecutorDemo("job2"),1,2,TimeUnit.SECONDS);
    }
}
