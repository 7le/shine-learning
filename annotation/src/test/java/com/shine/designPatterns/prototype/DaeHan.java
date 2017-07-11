package com.shine.designPatterns.prototype;

/**
 * 原型类 --浅拷贝
 * Created by 7le on 2017/5/8 0008.
 */
public class DaeHan implements Cloneable{

    //8种基本数据类型  => 浅拷贝
    //数组、容器对象、引用对象 => 深拷贝
    public Baby baby;

    public DaeHan() {}

    public DaeHan(Baby baby) {this.baby = baby;}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //使用原型模式创建对象比直接new一个对象在性能上要好的多，
    //因为Object类的clone方法是一个本地方法，
    //它直接操作内存中的二进制流，特别是复制大对象时，性能的差别非常明显。
    public static void main(String[] args) throws CloneNotSupportedException {
        DaeHan daeHan = new DaeHan(new Baby());
        DaeHan daeHan1 = (DaeHan) daeHan.clone();

        System.out.println(daeHan == daeHan1);
        System.out.println(daeHan.baby == daeHan1.baby);

    }
}
