package com.java.valueorreferences;

/**
 * java中方法参数传递方式是按值传递。
 * 如果参数是基本类型，传递的是基本类型的字面量值的拷贝。
 * 如果参数是引用类型，传递的是该参量所引用的对象在堆中地址值的拷贝。
 * Created by 7le on 2017/11/24
 */
public class Demo {

    public static void main(String[] args) {
        ABC abc = new ABC();
        AB ab = new AB();
        ab.setA(abcd);
        abc.setA(abcd);
        abcd = null;
        ab = null;
        System.out.println(abc.getA());  // 输出 12321
        System.out.println(abc.getAb().getA());  // java.lang.NullPointerException
    }

    static String abcd = "12321";

    static class ABC {
        private String a;
        private AB ab;

        public AB getAb() {
            return ab;
        }

        public void setAb(AB ab) {
            this.ab = ab;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }

    static class AB {
        private String a;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }
}
