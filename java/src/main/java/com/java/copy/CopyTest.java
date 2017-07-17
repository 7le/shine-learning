package com.java.copy;

/**
 * 深拷贝与浅拷贝  -- 原型模式
 * 使用原型模式创建对象比直接new一个对象在性能上要好的多，
 * 因为Object类的clone方法是一个本地方法，
 * 直接操作内存中的二进制流，特别是复制大对象时，性能的差别非常明显。
 * Created by 7le on 2017/7/16 0016.
 */
public class CopyTest {

    public static class Baby implements Cloneable{

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public static class DaeHan implements Cloneable {

        public Baby baby;
        public DaeHan() {
        }

        public DaeHan(Baby baby) {
            this.baby = baby;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public static class MinGuk implements Cloneable{

        public Baby baby;

        public MinGuk() {}

        public MinGuk(Baby baby) {this.baby = baby;}

        @Override
        protected Object clone() throws CloneNotSupportedException {
            MinGuk minGuk =  (MinGuk) super.clone();
            minGuk.baby = (Baby) baby.clone();
            return minGuk;
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        DaeHan daeHan = new DaeHan(new Baby());
        DaeHan daeHan1 = (DaeHan) daeHan.clone();
        //clone 会开辟新的内存 对于浅拷贝来说，类中的属性实际上并没有进行克隆，而只是进行一个赋值操作而已
        System.out.println(daeHan == daeHan1);
        System.out.println(daeHan.baby == daeHan1.baby);

        MinGuk minGuk = new MinGuk(new Baby());
        MinGuk minGuk1 = (MinGuk) minGuk.clone();

        System.out.println(minGuk == minGuk1);
        System.out.println(minGuk.baby == minGuk1.baby);

    }
}

