package com.java.scheduler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 7le on 2017/8/18.
 */
public class TimerDemo extends TimerTask {

    private final String jobName;

    public TimerDemo(String jobName) {
        this.jobName = jobName;
    }
     @Override
     public void run() {
         System.out.println("run the task => " + jobName);
     }

    public static void main(String[] args) {
        // 一种工具，线程用其安排以后在后台线程中执行的任务
        Timer timer = new Timer();
        timer.schedule(new TimerDemo("Job 1") , 1000 , 1000); // 一秒
        timer.schedule(new TimerDemo("Job 2") , 2000 , 2000); // 两秒
    }
}
