package com.design.patterns.strategy;

/**
 * Created by 7le on 2017/5/7 0007.
 */
public class Context {

    private Strategy strategy;

    //构造函数，要你使用哪个妙计
    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public void operate(){
        this.strategy.operate();
    }

    //客户端
    public static void main(String[] args) {
        //优点:可以动态的改变对象的行为
        //缺点:客户端必须知道所有的策略类，并自行决定使用哪一个策略类
        //     策略模式将造成产生很多策略类
        Context context;

        System.out.println("----------使用第一个锦囊---------------");
        context = new Context(new ConcreteStrategy1());
        context.operate();

        System.out.println("----------使用第二个锦囊---------------");
        context = new Context(new ConcreteStrategy2());
        context.operate();
    }
}
