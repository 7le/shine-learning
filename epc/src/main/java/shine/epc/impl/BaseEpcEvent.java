package shine.epc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shine.epc.EpcCode;
import shine.epc.EpcEvent;
import shine.epc.PerfTrace;

/**
 * epc 基础处理
 */
public abstract class BaseEpcEvent implements EpcEvent{

    private final static Logger LOG = LoggerFactory.getLogger(BaseEpcEvent.class);

    protected EpcEventParam param = null;

    /** 事件名称 */
    private String eventName;

    /** 性能调试 */
    protected PerfTrace perfTrace;

    /**
     * 事件名称，需要保证全局唯一
     *
     * @return 唯一标识的事件名称
     */
    public String getName() {
        if (eventName == null)
            eventName = this.getClass().getSimpleName();
        return eventName;
    }


    @Override
    public EpcEventParam getEventParam() {
        return param;
    }

    @Override
    public void setEventParam(EpcEventParam param) {
        this.param = param;
    }

    /**
     * 对事件参数进行的合法性检查， 确认参数是否正确，是否完整，是否拥有足够的权限执行此参数 等。
     */
    protected abstract int checkParam(EpcEventParam param) throws Exception;

    /**
     * 处理事件的业务逻辑
     */
    protected abstract void doBiz() throws Exception;

    /**
     * 执行事件，调用回调方法处理整个执行过程
     */
    public void execute() {
        // 开启 性能调试
        perfTrace = new PerfTrace(getClass().getSimpleName(), false);// release必须设置为false
        try {
            int errCode;
            perfTrace.begin();
            beforeExecute();
            perfTrace.step("before-事件运行");
            try {
                errCode = checkParam(param);
                perfTrace.step("checkParam()");
            } catch (Exception ex) {
                ex.printStackTrace();
                LOG.error("Unhandled check param exception:", ex);
                errCode = EpcCode.INVALID_PARAM;// 一个特定的错误号
            }

            if (errCode == EpcCode.SUCCESS) {
                doBiz();
                perfTrace.step("doBiz()");
            } else {
                doParamError(errCode);
                perfTrace.step("doParamError()");
            }
        } catch (Exception ex) {
            handleExecption(ex);
            perfTrace.step("handleException()");
            ex.printStackTrace();
        } finally {
            afterExecute();
            perfTrace.step("afterExecute()");
            // 清理参数
            param.clear();
            param = null;

            perfTrace.end();
        }
    }

    /**
     * 统一处理 参数 不合法的情况，由使用epc的各个应用去实现。
     *
     * @param errCode 错误号
     * @throws Exception
     */
    public abstract void doParamError(int errCode) throws Exception;

    /**
     * 事件执行之前被调用 可在此方法中准备上下文环境，进行统计等
     *
     * @throws Exception
     */
    protected void beforeExecute() {

    }

    /**
     * 事件执行之后被调用 可在此方法中销毁上下文环境，进行统计等
     */
    protected void afterExecute() {

    }
    /**
     * 处理事件执行过程中产生的异常
     *
     * @param ex 执行事件时抛出的异常
     */
    protected void handleExecption(Exception ex) {
        if (LOG.isErrorEnabled())
            LOG.error("event error:", ex);
        ex.printStackTrace();
        try {
            //TODO 通用消息回包
        } catch (Exception e) {
            LOG.error("send unknown err exception message error", e);
        }
    }


}
