package com.shine.designPatterns.state;

/**
 * 入口
 * Created by 7le on 2017/6/9
 */
public class Client {

    public static void main(String[] args) {
        State state=new ConcreteStateA();
        Context context=new Context(state);
        context.changeState();
        //转换状态
        context.changeState();
    }
}
