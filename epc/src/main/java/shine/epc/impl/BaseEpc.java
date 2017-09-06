package shine.epc.impl;

import shine.epc.Epc;
import shine.epc.EpcEvent;

/**
 * epc的基础实现
 */
public abstract class BaseEpc implements Epc{

    /**
     * 任务执行之前被调用
     */
    protected void beforeRun(Task t) { }

    /**
     * 任务执行之后被调用
     */
    protected void afterRun(Task t) { }

    /**
     * 在线程池中，执行Event的辅助类
     */
    protected class Task implements Runnable {
        EpcEvent te;
        Collision co;

        Task(EpcEvent event,Collision collision) {
            if (event == null)
                throw new NullPointerException("event is null.");

            te = event;
            co = collision;
        }

        public EpcEvent getEvent() {
            return te;
        }

        public Collision getCollision() {
            return co;
        }

        @Override
        public void run() {
            try {
                beforeRun(this);

                te.execute();
            } catch(Exception ex) {
                //ex.printStackTrace();
            } finally {
                afterRun(this);
            }
        }

        /**
         * 记录任务的运行时间
         */
        long tm, em;

        void beginProfile() {
            tm = System.currentTimeMillis();
        }

        void endProfile() {
            em = System.currentTimeMillis();
        }

        long runTimeMills() {
            return em - tm;
        }
    }
}
