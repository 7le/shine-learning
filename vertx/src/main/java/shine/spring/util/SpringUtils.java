package shine.spring.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 7le on 2017/11/6
 */
public class SpringUtils {

    private static ApplicationContext ctx = null;

    public static void init(String path) {
        ctx = new ClassPathXmlApplicationContext(path);
    }

    public static <T> T getBean(Class<T> cls) {
        return ctx.getBean(cls);
    }

    public static Object getBean(String name) {
        return ctx.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return ctx.getBean(name, requiredType);
    }
}
