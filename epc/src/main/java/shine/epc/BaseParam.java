package shine.epc;

import java.util.Map;

/**
 * 事件参数，改接口实例将作为参数注入Event中执行
 */
public abstract class BaseParam {

    protected abstract Map<String, Object> getMap();

    /**
     * 使用完后的清理方法
     */
    public void clear() {
        getMap().clear();
    }

}
