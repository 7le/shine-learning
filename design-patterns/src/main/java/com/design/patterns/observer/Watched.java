package com.design.patterns.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 被观察者
 * Created by 7le on 2017/6/7
 */
public class Watched extends Observable {

    public void notifyObservers(Object arg) {

        /**
         * 为避免并发冲突，设置了changed标志位changed =true，则当前线程可以通知所有观察者，内部同步块完了会设置为false；
         * 通知过程中，正在新注册的和撤销的无法通知到。
         */
        super.setChanged();

        /**
         * 事件触发，通知所有感兴趣的观察者
         */
        super.notifyObservers(arg);
    }


    public static void main(String[] args) {
        Watched watched = new Watched();
        //方式一
        Watcher watcherDemo = new Watcher();
        watched.addObserver(watcherDemo);

        //方式二
        watched.addObserver(new Observer(){
            @Override
            public void update(Observable o, Object arg) {
                if(arg.toString().equals("close")){
                    System.out.println("close......");
                }
            }
        });
        //通知观察者
        watched.notifyObservers("likes");

        watched.notifyObservers("likes");

    }

}
