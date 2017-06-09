package com.shine.designPatterns.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 * Created by 7le on 2017/6/7
 */
public class Watcher implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if(arg.toString().equals("likes")){
            System.out.println("点赞...");
        }
    }
}
