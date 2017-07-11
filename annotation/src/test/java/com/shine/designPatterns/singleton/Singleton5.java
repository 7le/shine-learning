package com.shine.designPatterns.singleton;

/**
 * 内部静态类
 * Created by 7le on 2017/5/14 0014.
 */
public class Singleton5 {

    private static class LazyHolder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    private Singleton5 (){}

    public static Singleton5 getInstance() {
        return LazyHolder.INSTANCE;
    }
}
