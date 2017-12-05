package shine.spring;

import shine.spring.util.SpringUtils;
import shine.spring.vertx.HttpVerticle;

/**
 * @description: 入口
 * @author : 7le
 * @date: 2017/11/16
 */
public class ServerMain {

    private static final String APPLICATION_FILE = "spring.xml";

    public static void main(String[] args) {
        initSpring();
        initVertx();

    }

    private static void initSpring() {
        SpringUtils.init(APPLICATION_FILE);
    }

    private static void initVertx() {
        HttpVerticle.deploy();
    }
}
