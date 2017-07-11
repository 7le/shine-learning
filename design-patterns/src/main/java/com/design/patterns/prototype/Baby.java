package com.design.patterns.prototype;

/**
 * Created by 7le on 2017/5/8 0008.
 */
public class Baby implements Cloneable{

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
