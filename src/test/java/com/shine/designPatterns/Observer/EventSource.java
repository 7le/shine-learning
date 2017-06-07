package com.shine.designPatterns.Observer;

import java.util.EventObject;
import java.util.Vector;

/**
 * 事件源
 * Created by 7le on 2017/6/7
 */
public class EventSource {

    //监听器列表，监听器的注册则加入此列表
    private Vector<MyListener> ListenerList = new Vector<MyListener>();

    //注册监听器
    public void addListener(MyListener eventListener) {
        ListenerList.add(eventListener);
    }

    //撤销注册
    public void removeListener(MyListener eventListener) {
        ListenerList.remove(eventListener);
    }

    //接受外部事
    public void notifyListenerEvents(MyEvent event) {
        for (MyListener eventListener : ListenerList) {
            eventListener.handleEvent(event);
        }
    }

    public void onClose(MyListener eventListener){
        ListenerList.add(eventListener);
    }

    public void doClose(){
        this.notifyListenerEvents(new MyEvent("close"));
    }
}
