package com.java.delayQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时消息实体类
 */
public class Message implements Delayed {

    private int id;
    private String body;  //消息内容
    private long excuteTime;//执行时间 --绝对时间

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.excuteTime - System.nanoTime(), TimeUnit.NANOSECONDS);

    }

    /**
     * 根据执行时间排序
     */
    @Override
    public int compareTo(Delayed o) {
        Message msg = (Message) o;
        int sort= this.excuteTime > msg.excuteTime ? 1 : this.excuteTime < msg.excuteTime ? -1 : 0;
        return sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(long excuteTime) {
        this.excuteTime = excuteTime;
    }

    public Message(int id, String body, long delayTime) {
        this.id = id;
        this.body = body;
        this.excuteTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }


}
