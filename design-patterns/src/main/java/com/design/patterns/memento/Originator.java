package com.design.patterns.memento;

/**
 * 原发器
 * Created by 7le on 2017/6/15 .
 */
public class Originator {

    private String state;

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Memento createMemento(){
        return new Memento(this.state);
    }
    public void restoreMemento(Memento memento){
        this.setState(memento.getState());
    }
}
