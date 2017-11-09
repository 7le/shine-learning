package shine.spring;

import shine.spring.util.SpringUtils;
import shine.spring.vertx.HttpVerticle;

/**
 * 入口
 * Created by 7le on 2017/11/6
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
