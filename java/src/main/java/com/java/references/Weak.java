package com.java.references;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * 弱引用
 *
 * -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log 输出gc log
 */
public class Weak {
    /**
     * 弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。
     * 在垃圾回收器线程扫描它 所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，
     * 不管当前内存空间足够与否，都会回收它的内存。
     * 不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
     * <p>
     * 在同样需要10G内存的场景下 而弱引用WeekReference引用的对象，如果仅仅只被弱引用，而没有被强引用的话，
     * 在下一次GC时，就会回收该对象占用的内存，所以不会内存溢出。
     * 打断点可以发现 GC后 referent会为null
     */
    public static void main(String[] args) {
        Weak weak=new Weak();
        WeakReference<Object> wr = new WeakReference<>(weak);

        long beginTime = System.nanoTime();
        LinkedList<WeakReference<byte[]>> list = new LinkedList<>();
        for (int i = 0; i < 1024 * 10; i++) {
            list.add(new WeakReference<>(new byte[1024 * 1024]));
        }
        long endTime = System.nanoTime();
        System.out.println("time: " + (endTime - beginTime));

        //根据gc log 已经发生过gc 且list中的对象的referent也为null 但是单独的wr没有被回收
        System.out.println(wr.get());
    }
}
