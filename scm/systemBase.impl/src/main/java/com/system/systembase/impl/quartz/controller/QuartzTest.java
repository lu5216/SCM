package com.system.systembase.impl.quartz.controller;

import com.system.common.utils.DateUtil;
import com.system.common.utils.QuartzJobNameUtil;
import com.system.common.component.ScheduleComponent;
import com.system.systembase.impl.quartz.jobBean.TestJobBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lutong
 * @data 2025-3-5 005 10:53
 */

@Slf4j
@RestController
@Api(tags = "接口-定时任务api接口")
@RequestMapping("/quartz")
@Controller
public class QuartzTest {

    @Autowired
    private ScheduleComponent scheduleComponent;

    @ApiOperation(value = "测试")
    @GetMapping(value = "/testQuartz")
    public void testQuartz(@RequestParam(value = "time") String time) {
        String jobName = QuartzJobNameUtil.getTestJobName();
        //job开启组
        String jobGroupName = "CANCEL_ORDER";
        //任务执行时间
        Date jobData = DateUtil.getMinAfter(time, 1);
        //添加参数
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        //添加任务前 先执行删除操作，确保新增成功
        scheduleComponent.deleteJob(jobName, jobGroupName);
        //添加定时任务
        scheduleComponent.addJobAtTime(TestJobBean.class, jobName, jobGroupName, jobData, dataMap);

    }
}
