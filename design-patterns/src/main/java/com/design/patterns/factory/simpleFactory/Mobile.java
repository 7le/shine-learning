package com.design.patterns.factory.simpleFactory;

import java.lang.annotation.*;

/**
 * Created by 7le on 2017/4/28 0028.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Mobile {
    String type() default "";
}
