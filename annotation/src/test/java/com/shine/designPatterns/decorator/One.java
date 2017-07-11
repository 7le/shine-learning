package com.shine.designPatterns.decorator;

/**
 * 只有一个具体装饰类的时候，装饰类和具体装饰类可以合并一个类
 * Created by 7le on 2017/5/3 0003.
 */
public class One extends Phone{

    private Phone phone;

    public One(Phone phone){
        this.phone = phone;
    }

    @Override
    protected void call() {
        phone.call();
        bye();
    }

    //附加的装饰性功能
    private void bye(){
        System.out.println("bye......");
    }

    public static void main(String[] args) {
        One one=new One(new Iphone());
        one.call();
    }
}
