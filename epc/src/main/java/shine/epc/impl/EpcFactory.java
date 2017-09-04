package shine.epc.impl;

import shine.epc.Epc;

/**
 * Epc工厂
 */
public class EpcFactory {

    /**
     * 采用线程池，并发处理事件的EPC
     */
    public static Epc createEpcThreadPool() {
        return createEpcThreadPool(EpcThreadPool.DEFAULT_POOL_SIZE, EpcThreadPool.DEFAULT_POOL_SIZE);
    }

    /**
     * 采用线程池，并发处理事件的EPC
     * @param maxPoolSize 线程池最大线程数
     */
    public static Epc createEpcThreadPool(int maxPoolSize) {
        return createEpcThreadPool(maxPoolSize, maxPoolSize);
    }

    /**
     * 采用线程池，并发处理事件的EPC
     * @param corePoolSize 线程池最小线程数
     * @param maxPoolSize 线程池最大线程数
     */
    public static Epc createEpcThreadPool(int corePoolSize, int maxPoolSize) {
        Epc epc = new EpcThreadPool(corePoolSize, maxPoolSize);
        //TODO set epc to jmx
        //Registry.getJmxManager().registerMBean(new TsMBean<Epc>(epc), "EPC");
        return epc;
    }

    /**
     * 简单的epc，按时间排序，顺序处理
     */
    public static Epc createEpcSimple() {
        return new EpcSimple();
    }

}
