package shine.spring;

import shine.spring.util.SpringUtils;
import shine.spring.vertx.Server;

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
        Server.deploy();
    }
}
