package reflection;

import com.google.common.collect.Lists;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class Demo {

    @Test
    public void DynamicProxyDemo(){
        InvocationHandler invocationHandler = new MyInvocationHandler();

        //guava 简化的代理
        Shine shine= Reflection.newProxy(Shine.class, invocationHandler);
        shine.sunshine();

        //jdk动态代理
        Shine jdkShine = (Shine) Proxy.newProxyInstance(
                Shine.class.getClassLoader(),
                new Class<?>[]{Shine.class},
                invocationHandler);
        jdkShine.sunshine();
    }

    @Test
    public void InvokableDemo() throws NoSuchMethodException {
        Invokable invokable = Invokable.from(SunShine.class.getMethod("add", int.class, int.class));
        System.out.println(invokable.isPublic()); // true
        System.out.println(invokable.getDeclaringClass()); // class reflection.SunShine
        System.out.println(invokable.getParameters()); // [int arg0, int arg1]
        System.out.println(invokable.getOwnerType()); // reflection.SunShine
        System.out.println(invokable.getExceptionTypes()); // [java.lang.NumberFormatException]
        System.out.println(invokable.getReturnType()); // int
        System.out.println(invokable.getModifiers()); // 1
        System.out.println(invokable.getName()); // add
        System.out.println(invokable.isOverridable()); // true
        System.out.println(invokable.isVarArgs()); // false
        System.out.println(invokable.isPublic()); // true
        System.out.println(invokable.isAbstract()); // false
        System.out.println(invokable.isAccessible()); // false
        System.out.println(invokable.isAnnotationPresent(Sun.class)); // true
        System.out.println(invokable.isStatic()); // false
    }

    @Test
    public void testTypeToken() {
        ArrayList<String> stringList = Lists.newArrayList();
        ArrayList<Integer> intList = Lists.newArrayList();

        // 类型被擦除了
        System.out.println("stringList: "+ stringList.getClass());
        System.out.println("intList: "+ intList.getClass());
        System.out.println(stringList.getClass().isAssignableFrom(intList.getClass()));

        // 利用TypeToken解析原来的类型
        TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>() {};
        TypeToken<?> genericType = typeToken.resolveType(ArrayList.class.getTypeParameters()[0]);

        System.out.println(genericType.getType()); // class java.lang.String
    }
}
