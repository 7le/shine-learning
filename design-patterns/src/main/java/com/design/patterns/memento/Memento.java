package com.design.patterns.memento;

/**
 * 备忘录
 * Created by 7le on 2017/6/15 .
 */
public class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
