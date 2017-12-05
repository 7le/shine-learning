package shine.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;
import java.util.concurrent.TimeUnit;

/**
 * @description: ZkClient的使用测试
 * @author : 7le
 * @date: 2017/12/5
 */
public class ZkClientTest {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("192.168.20.52:2181,192.168.20.53:2181");
        String node = "/myapp";

        // 订阅监听事件
        childChangesListener(zkClient, node);
        dataChangesListener(zkClient, node);
        stateChangesListener(zkClient);

        if (!zkClient.exists(node)) {
            zkClient.createPersistent(node, "hello zookeeper");
        }
        System.out.println((String) zkClient.readData(node));

        zkClient.updateDataSerialized(node, str -> str + "-123");
        System.out.println((String) zkClient.readData(node));

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅children变化
     */
    public static void childChangesListener(ZkClient zkClient, final String path) {
        zkClient.subscribeChildChanges(path, (parentPath, currentChildren) ->
                System.out.println("Children of path " + parentPath + ":" + currentChildren));
    }

    /**
     * 订阅节点数据变化
     */
    public static void dataChangesListener(ZkClient zkClient, final String path){
        zkClient.subscribeDataChanges(path, new IZkDataListener(){

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }

        });
    }

    /**
     * 订阅状态变化
     */
    public static void stateChangesListener(ZkClient zkClient){
        zkClient.subscribeStateChanges(new IZkStateListener() {

            @Override
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                System.out.println("handleStateChanged");
            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {
                System.out.println("handleSessionEstablishmentError");
            }

            @Override
            public void handleNewSession() throws Exception {
                System.out.println("handleNewSession");
            }
        });
    }
}
