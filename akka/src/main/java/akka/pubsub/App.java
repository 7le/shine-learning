package akka.pubsub;

/**
 * 入口
 */
public class App {

    public static void main(String[] args) throws Exception {

        // 启动一个Client
        EventClient.main(new String[0]);

        EventInterceptorMain.main(new String[] { "2851" });
        EventInterceptorMain.main(new String[] { "2852" });

        EventProcessorMain.main(new String[]{"2951"});
        EventProcessorMain.main(new String[]{"2952"});
        EventProcessorMain.main(new String[]{"2953"});
        EventProcessorMain.main(new String[]{"2954"});
        EventProcessorMain.main(new String[]{"2955"});
    }
}
