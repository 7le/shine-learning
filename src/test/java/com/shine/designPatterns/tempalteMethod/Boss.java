package com.shine.designPatterns.tempalteMethod;

/**
 * Created by 7le on 2017/5/9 0009.
 */
public class Boss extends AbstractPerson {
    @Override
    protected void dressUp() {
        System.out.println("穿上西装...");
    }

    @Override
    protected void eatBreakfast() {
        System.out.println("吃完精致早饭...");
    }

    @Override
    protected void doSomethings() {
        System.out.println("叫程序员干活...");
    }
}
