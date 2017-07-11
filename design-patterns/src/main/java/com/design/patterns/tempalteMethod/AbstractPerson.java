package com.design.patterns.tempalteMethod;

/**
 * 抽象类
 * Created by 7le on 2017/5/9 0009.
 */
public abstract class AbstractPerson {

    //抽象类定义整个流程骨架
    //
    public void prepareGoCompany(){
        dressUp();
        if(personWants()){
            eatBreakfast();
        }

        doSomethings();
    }
    //以下是不同子类根据自身特性完成的具体步骤
    protected abstract void dressUp();
    protected abstract void eatBreakfast();
    protected abstract void doSomethings();

    /* hook */
    //该方法可以控制模版方法中的逻辑
    //该方法提供默认的实现。子类不是必须实现它。
    public boolean personWants() {
        return true; // default implementation
    }

    /**
     * 模板方法模式通过把不变的行为搬移到超类，去除了子类中的重复代码。
     * 子类实现算法的某些细节，有助于算法的扩展。
     */
    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.prepareGoCompany();

        Programmer programmer  = new Programmer();
        programmer.prepareGoCompany();
    }

}
