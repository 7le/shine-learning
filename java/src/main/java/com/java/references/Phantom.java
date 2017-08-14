package com.java.references;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用
 */
public class Phantom {

    /**
     * 如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收。
     */
    public static void main(String[] args) {
        Test obj = new Test();
        ReferenceQueue<Object> rq = new ReferenceQueue<Object>();
        PhantomReference<Object> pr = new PhantomReference<Object>(obj, rq);
        obj=null; //撤掉强引用
        System.out.println(pr.get());
        System.gc();                //启动GC
        System.out.println(pr.get());

    }
}
