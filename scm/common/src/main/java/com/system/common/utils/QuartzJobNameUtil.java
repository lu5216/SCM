package com.system.common.utils;

import java.util.Date;

/**
 * @author lutong
 * @data 2025-3-5 005 10:57
 */

public class QuartzJobNameUtil {

    // job 名称前缀
    private static final String JOB_NAME = "QUARTZ_JOB";


    /**
     * 生成唯一的 job名称
     */
    public static String getTestJobName() {
        //生成拍卖job名称
        return JOB_NAME + DateUtil.getDateForFormat(new Date(), DateUtil.YMD_HMS) + (1 + (int) (Math.random() * 10000));
    }
}
