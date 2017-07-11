package com.design.patterns.composite;

/**
 * 组合中的对象声明接口 （Component ）
 * Created by 7le on 2017/6/16
 */
public abstract class Market {

    String name;

    public abstract void add(Market m);

    public abstract void remove(Market m);

    public abstract void payByCard();

}
