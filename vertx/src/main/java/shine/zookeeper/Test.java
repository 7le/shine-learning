package shine.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @description: test
 * @author : 7le
 * @date: 2017/12/5
 */
public class Test {

    /**
     * 会话超时时间，设置为与系统默认时间一致
     */
    private static final int SESSION_TIMEOUT = 30 * 1000;

    /**
     * 创建 ZooKeeper 实例
     */
    private ZooKeeper zk;

    /**
     * 创建 Watcher 实例
     */
    private Watcher wh = new Watcher() {
        /**
         * Watched事件
         */
        @Override
        public void process(WatchedEvent event) {
            System.out.println("WatchedEvent >>> " + event.toString());
        }
    };

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        Test dm = new Test();
        dm.createZKInstance();
        dm.zkOperations();
        dm.zkClose();
    }

    /**
     * 初始化 ZooKeeper 实例
     */
    private void createZKInstance() throws IOException {
        // 连接到ZK服务，多个可以用逗号分割写
        zk = new ZooKeeper("192.168.20.52:2181,192.168.20.53:2181", Test.SESSION_TIMEOUT, this.wh);

    }

    private void zkOperations() throws IOException, InterruptedException, KeeperException {
        System.out.println("\n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
        zk.create("/zoo2", "myData2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("\n2. 查看是否创建成功： ");
        // 添加Watch
        System.out.println(new String(zk.getData("/zoo2", this.wh, null)));

        // 前面一行我们添加了对/zoo2节点的监视，所以这里对/zoo2进行修改的时候，会触发Watch事件。
        System.out.println("\n3. 修改节点数据 ");
        zk.setData("/zoo2", "7le".getBytes(), -1);

        // 这里再次进行修改，则不会触发Watch事件，这就是我们验证ZK的一个特性“一次性触发”，也就是说设置一次监视，只会对下次操作起一次作用。
        System.out.println("\n3-1. 再次修改节点数据 ");
        zk.setData("/zoo2", "7le-19960301".getBytes(), -1);

        System.out.println("\n4. 查看是否修改成功： ");
        System.out.println(new String(zk.getData("/zoo2", false, null)));

        System.out.println("\n5. 删除节点 ");
        zk.delete("/zoo2", -1);

        System.out.println("\n6. 查看节点是否被删除： ");
        System.out.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
    }

    private void zkClose() throws InterruptedException {
        zk.close();
    }
}
