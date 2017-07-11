package com.design.patterns.prototype;

/**
 * 原型类 --深拷贝
 * Created by 7le on 2017/5/8 0008.
 */
public class MinGuk implements Cloneable{

    //8种基本数据类型  => 浅拷贝
    //数组、容器对象、引用对象 => 深拷贝
    public Baby baby;

    public MinGuk() {}

    public MinGuk(Baby baby) {this.baby = baby;}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MinGuk minGuk =  (MinGuk) super.clone();
        minGuk.baby = (Baby) baby.clone();
        return minGuk;
    }

    //使用原型模式创建对象比直接new一个对象在性能上要好的多，
    //因为Object类的clone方法是一个本地方法，
    //它直接操作内存中的二进制流，特别是复制大对象时，性能的差别非常明显。
    public static void main(String[] args) throws CloneNotSupportedException {
        MinGuk minGuk = new MinGuk(new Baby());
        MinGuk minGuk1 = (MinGuk) minGuk.clone();

        System.out.println(minGuk == minGuk1);
        System.out.println(minGuk.baby == minGuk1.baby);

    }
}
