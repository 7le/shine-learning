package com.design.patterns.tempalteMethod;

/**
 * 具体类ConcreteClass
 * Created by 7le on 2017/5/9 0009.
 */
public class Programmer extends AbstractPerson{

    private boolean flag;

    @Override
    protected void dressUp() {
        System.out.println("穿上T桖短裤...");
        this.flag=false;
    }

    @Override
    protected void eatBreakfast() {
        System.out.println("叼一片面包...");
    }

    @Override
    protected void doSomethings() {
        System.out.println("开始coding...");
    }

    @Override
    public boolean personWants() {
        return flag; // default implementation
    }
}
