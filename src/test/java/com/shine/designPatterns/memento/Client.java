package com.shine.designPatterns.memento;

/**
 * 入口
 * Created by 7le on 2017/6/15 .
 */
public class Client {

    public static void main(String[] args) {

        //返回上一步的状态
        Originator originator = new Originator();
        originator.setState("状态1");
        System.out.println("初始状态:" + originator.getState());

        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());
        originator.setState("状态2");

        System.out.println("改变后状态:" + originator.getState());
        originator.restoreMemento(caretaker.getMemento());
        System.out.println("恢复后状态:" + originator.getState());
    }
}
