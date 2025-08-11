package com.system.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lutong
 * @data 2025-1-22 022 14:25
 */

public class DateUtil {

    // 设置日期格式
    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String YMD_H = "yyyy-MM-dd HH";
    public static final String YMD = "yyyy-MM-dd";
    public static final String YM = "yyyy-MM";
    public static final String MD = "MM-dd";
    public static final String HMS = "HH:mm:ss";
    public static final String HM = "HH:mm";
    public static final String Y = "yyyy";

    /**
     * 判断当前时间距离第二天凌晨的秒数
     *
     * @return 返回值单位为[s:秒]
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }


    /**
     *  日期转字符串
     * @param date
     * @param format
     * @return
     */
    public static String getDateForFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     *  字符串转日期
     * @param dateStr
     * @param format
     * @return
     */
    public static Date getFormatForDate(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 比较两个时间的大小
     * date1小于date2返回-1，date1大于date2返回1，相等返回0
     * @param date1
     * @param date2
     * @return
     */
    public Integer compareToTime1AndTime2 (Date date1,Date date2){
        return date1.compareTo(date2);
    }


    /**
     *  获取指定时间x分钟后的Date
     * @param num
     * @param time
     * @return
     */
    public static Date getMinAfter(String time, Integer num) {
        Date date = getFormatForDate(time, YMD_HMS);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, num);
        Date newDate = calendar.getTime();
        return newDate;
    }
}
