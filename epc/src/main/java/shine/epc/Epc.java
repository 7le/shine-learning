package shine.epc;

/**
 * @author hq
 * @Description: 事件处理中心接口
 * @date 2017年8月28日
 * @since v1.0.0
 */
public interface Epc {

    /**
     * 添加一个待处理事件到epc中
     * EPC保证拥有相同冲突体的事件不会被并发被执行
     */
    void pushEvent(EpcEvent event);

    /**
     * 处理完所有待处理事件后，关闭epc
     */
    void shutdown(EpcEvent event);

    /**
     * 立刻关闭epc，丢弃所有待处理事件
     */
    void shutdownNow(EpcEvent event);
}
