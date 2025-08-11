package com.system.systembase.impl.quartz.jobBean;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author lutong
 * @data 2025-3-5 005 11:14
 */

@Component
public class TestJobBean extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {

            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            //获取参数
            int id = Integer.parseInt(jobDataMap.get("id").toString());
            //执行业务逻辑
            System.out.println("执行业务逻辑-" + id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

//    @Scheduled(cron="0/5 * *  * * ? ") // 每5秒执行一次
//    public void jobRun() {
//        System.out.println("执行业务逻辑!");
//    }
}
