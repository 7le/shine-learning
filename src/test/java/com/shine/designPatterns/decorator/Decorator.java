package com.shine.designPatterns.decorator;

/**
 * 抽象装饰类
 * 它也是抽象构件类的子类，用于给具体构件增加职责，但是具体职责在其子类中实现。
 * 它维护一个指向抽象构件对象的引用，
 * 通过该引用可以调用装饰之前构件对象的方法，并通过其子类扩展该方法，以达到装饰的目的。
 * Created by 7le on 2017/5/2 0002.
 */
abstract class Decorator extends Phone{

    private Phone phone;

    public Decorator(Phone phone){
        this.phone = phone;
    }

    @Override
    protected void call(){
        phone.call();
    }
}
