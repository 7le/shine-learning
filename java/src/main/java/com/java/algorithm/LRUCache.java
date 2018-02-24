package com.java.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRUCache, 当需要淘汰元素时,淘汰最近最少使用的那个元素
 *
 * @param <E> 元素类型
 */
public class LRUCache<E> {

    private LinkedHashMap<E, E> cache;
    private boolean flag = false;
    private volatile E result = null;

    /**
     * 构建一个LRUCache
     *
     * @param capacity cache的容量
     */
    public LRUCache(int capacity) {
        //TODO
        cache = new LinkedHashMap<E, E>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                flag = size() > capacity;
                if (flag) {
                    result = (E) eldest.getValue();
                }
                return flag;
            }
        };
    }

    /**
     * 放一个元素到Cache中,如有必要淘汰已有的元素
     *
     * @param e 新增的元素
     * @return 如果有淘汰的元素, 返回被淘汰的元素, 否则返回null
     */
    public E put(E e) {
        // TODO
        cache.put(e, e);
        return result;
    }

    /**
     * Cache是否包含指定元素
     *
     * @param e 指定元素
     * @return 包含返回true, 否则返回false
     */
    public boolean contains(E e) {
        //TODO
        if (cache.containsValue(e)) {
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        // TODO 补充至少一个测试用例
        LRUCache cache = new LRUCache(2);
        System.out.println(cache.put(1));
        //Cache是否包含指定元素
        System.out.println(cache.contains(1));
        System.out.println(cache.put(2));
        //3替代1 输出1
        System.out.println(cache.put(3));
        //4替代2 输出2
        System.out.println(cache.put(4));
        //Cache是否包含指定元素
        System.out.println(cache.contains(1));
    }
}