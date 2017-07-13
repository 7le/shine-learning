package com.spring.jdkDynamicAopProxy;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 7le
 * @Description: 抽象角色（动态代理只能代理接口）
 * @date 2017年4月13日
 */
public class Utils {

    public static void generateProxyClassFile(Object object) {
        byte[] classFile = sun.misc.ProxyGenerator.generateProxyClass(
                "$MyProxy", object.getClass().getInterfaces());
        FileOutputStream out = null;
        String path = System.getProperty("user.dir")+"/target/test-classes/com/shine/jdkDynamicAopProxy/$MyProxy.class";
        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
