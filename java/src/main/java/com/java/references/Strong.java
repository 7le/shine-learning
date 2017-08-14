package com.java.references;

/**
 * 强引用
 */
public class Strong {

    public static void main(String[] args) {
        Test obj = new Test();
        //启动GC
        System.gc();
        System.out.println(obj);
    }
}
