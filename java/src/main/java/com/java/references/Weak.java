package com.java.references;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 */
public class Weak {
    /**
     * 弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。
     * 在垃圾回收器线程扫描它 所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，
     * 不管当前内存空间足够与否，都会回收它的内存。
     * 不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
     */
    public static void main(String[] args) {
        Test obj = new Test();
        WeakReference<Object> wr = new WeakReference<Object>(obj);
        obj=null; //撤掉强引用
        System.out.println(wr.get());
        System.gc();                //启动GC
        System.out.println(wr.get());

    }
}
