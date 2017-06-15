package com.shine.designPatterns.memento;

/**
 * 入口 多步回退
 * Created by 7le on 2017/6/15 .
 */
public class Client1 {

    public static void main(String[] args) {
        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator();

        originator.setState("状态1");
        caretaker.setMementoList(originator.createMemento());

        originator.setState("状态2");
        caretaker.setMementoList(originator.createMemento());

        originator.setState("状态3");
        caretaker.setMementoList(originator.createMemento());

        System.out.println("现在的状态： "+originator.getState());

        //退回到最开始
        originator.restoreMemento(caretaker.getMementoList(0));
        System.out.println("退回最开始的状态： "+originator.getState());
    }
}