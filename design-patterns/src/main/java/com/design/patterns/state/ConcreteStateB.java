package com.design.patterns.state;

/**
 * 实现状态类
 * Created by 7le on 2017/6/9
 */
public class ConcreteStateB implements State {
    @Override
    public void changeState(Context context) {
        System.out.println("this is ConcreteStateB");

        context.setState(new ConcreteStateA());
    }
}
