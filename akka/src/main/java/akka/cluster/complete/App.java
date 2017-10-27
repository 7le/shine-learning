package akka.cluster.complete;

import akka.cluster.complete.be.BackendMain;
import akka.cluster.complete.fe.FrontendMain;

/**
 * @author 7le
 * @Description: 入口
 * @date 2017年10月27日
 * @since v1.0.0
 */
public class App {

    public static void main(String[] args) {
        // starting 3 backend nodes and 1 frontend node
        BackendMain.main(new String[]{"2551"});
        BackendMain.main(new String[]{"2552"});
        BackendMain.main(new String[0]);
        FrontendMain.main(new String[0]);
    }
}
