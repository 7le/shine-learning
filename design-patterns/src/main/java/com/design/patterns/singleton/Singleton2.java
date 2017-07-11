package com.design.patterns.singleton;

/**
 * 加上同步锁的 懒汉单例
 * Created by 7le on 2017/5/14 0014.
 */
public class Singleton2 {

    private Singleton2(){}

    private static Singleton2 single2 =null;

    public synchronized static Singleton2 getInstance(){
        if(single2==null){
            single2 = new Singleton2();
        }
        return single2;
    }

}
