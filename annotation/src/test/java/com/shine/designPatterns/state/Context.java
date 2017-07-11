package com.shine.designPatterns.state;

/**
 * 环境角色类
 * Created by 7le on 2017/6/9
 */
public class Context {
    private State state;

    public void changeState(){
        state.changeState(this);
    }


    public Context(State state) {
        super();
        this.state = state;
    }


    public State getState() {
        return state;
    }


    public void setState(State state) {
        this.state = state;
    }
}
