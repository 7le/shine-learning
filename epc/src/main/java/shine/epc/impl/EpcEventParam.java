package shine.epc.impl;


import shine.epc.BaseParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件参数，改接口实例将作为参数注入Event中执行
 */
public class EpcEventParam extends BaseParam {

    private int seqSeries;// unsigned short
    private long seqNo; // unsigned int
    private int tid;

    private HashMap<String, Object> map;
    private long timestamp;

    public EpcEventParam() {
        timestamp = System.currentTimeMillis();
        map = new HashMap<String, Object>();
    }

    @Override
    protected Map<String, Object> getMap() {
        return map;
    }

    public void setSeqSeries(int seqSeries) {
        this.seqSeries = seqSeries;
    }

    public int getSeqSeries() {
        return seqSeries;
    }

    public void setSeqNo(long seqNo) {
        this.seqNo = seqNo;
    }

    public long getSeqNo() {
        return seqNo;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTid() {
        return tid;
    }
}
