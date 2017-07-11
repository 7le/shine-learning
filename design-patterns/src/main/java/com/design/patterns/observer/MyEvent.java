package com.design.patterns.observer;

import java.util.EventObject;

/**
 * 事件
 * Created by 7le on 2017/6/7
 */
public class MyEvent extends EventObject{

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MyEvent(Object source) {
        super(source);
    }

    public void doEvent(){
        System.out.println("通知一个事件源 source :"+ this.getSource());
    }
}
