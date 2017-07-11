package com.design.patterns.decorator;

/**
 * 具体构件
 * 它是抽象构件类的子类，用于定义具体的构件对象，
 * 实现了在抽象构件中声明的方法，装饰器可以给它增加额外的职责（方法）。
 * Created by 7le on 2017/5/2 0002.
 */
public class Iphone extends Phone{

    @Override
    protected void call() {
        System.out.println("开始呼叫......");
    }
}
