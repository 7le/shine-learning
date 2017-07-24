package objects;

import com.google.common.base.Objects;
import org.junit.Test;

public class demo {

    /**
     * 判断两个对象是否相等
     * 首先判断a b是否是同一个对象，如果是直接返回相等，
     * 如果不是同一对象再判断a不为null并且a.equals(b).
     * 这样做既考虑了性能也考虑了null空指针的问题。
     */
    @Test
    public void equal(){
        Object a = null;
        Object b = new Object();
        boolean result = Objects.equal(a, b);
        System.out.println(result);
    }
}
