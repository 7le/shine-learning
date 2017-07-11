package com.design.patterns.decorator;

/**
 * 具体装饰类2
 * Created by 7le on 2017/5/2 0002.
 */
public class ConcreteDecorator2 extends Decorator{


    public ConcreteDecorator2(Phone phone) {
        super(phone);
    }

    @Override
    protected void call(){
      super.call();
      hello();
    }

    //附加的装饰性功能
    private void hello(){
        System.out.println("hello......");
    }

}
