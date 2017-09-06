package shine;

import shine.epc.EpcCode;
import shine.epc.impl.BaseEpcEvent;
import shine.epc.impl.EpcEventParam;

/**
 * epc 测试
 */
public class Task extends BaseEpcEvent {

    @Override
    protected int checkParam(EpcEventParam param) throws Exception {
        System.out.println(1);
        System.out.println(super.getName());
        return EpcCode.SUCCESS;
    }

    @Override
    protected void doBiz() throws Exception {
        System.out.println(2);
    }

    @Override
    public void doParamError(int errCode) throws Exception {
        System.out.println(3);
    }
}
