package shine.spring.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author hq
 * @Description: 广告统计 离线计算
 * @date 2017年11月16日
 * @since v2.0.0
 */
public class VertxJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        System.out.println("start quartz ");
    }

    private ApplicationContext getApplicationContext(final JobExecutionContext jobexecutioncontext) {
        try {
            return (ApplicationContext) jobexecutioncontext.getScheduler().getContext().get("applicationContextKey");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
