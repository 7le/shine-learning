package com.shine.designPatterns.facade;

/**
 * 门面类
 * Created by 7le on 2017/5/10 0010.
 */
public class ModuleFacade {

    ModuleA a = new ModuleA();
    ModuleB b = new ModuleB();
    ModuleC c = new ModuleC();
    /**
     * 下面这些是A、B、C模块对子系统外部提供的方法
     */
    public void a1(){
        a.a1();
    }
    public void b1(){
        b.b1();
    }
    public void c1(){
        c.c1();
    }
    public void all(){
        a.a1();
        b.b1();
        c.c1();
    }

    //客户端调用
    public static void main(String[] args) {
        //客户端只需要跟Facade类交互,不需要了解ABC的实现或存在
        //实现了客户端和子系统中A、B、C模块的解耦
        ModuleFacade moduleFacade=new ModuleFacade();
        moduleFacade.all();
    }
}