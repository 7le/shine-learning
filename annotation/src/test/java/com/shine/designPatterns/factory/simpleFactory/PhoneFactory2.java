package com.shine.designPatterns.factory.simpleFactory;

import org.reflections.Reflections;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 7le on 2017/4/28 0028.
 */
public class PhoneFactory2 {
    private static Map<String, Class> allPhones;

    static {
        Reflections reflections = new Reflections("com.shine.designPattens.factory.simpleFactory");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Mobile.class);
        allPhones = new ConcurrentHashMap<String, Class>();
        for (Class<?> classObject : annotatedClasses) {
            Mobile mobile = (Mobile) classObject.getAnnotation(Mobile.class);
            allPhones.put(mobile.type(), classObject);
        }
        allPhones = Collections.unmodifiableMap(allPhones);
    }
}
