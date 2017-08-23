package com.awifi.np.admin.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    public static final int DATE_INTERVAL_DAY = 1; // 日
    public static final int DATE_INTERVAL_WEEK = 2; // 周
    public static final int DATE_INTERVAL_MONTH = 3; // 月
    public static final int DATE_INTERVAL_YEAR = 4; // 年
    public static final int DATE_INTERVAL_HOUR = 5; // 小时
    public static final int DATE_INTERVAL_MINUTE = 6; // 分钟
    public static final int DATE_INTERVAL_SECOND = 7; // 秒
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMS_SSS = "HH:mm:ss.SSS";

    private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    
    private static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取年字符串YYYY
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午11:30:11
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午11:30:47
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午11:31:17
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午11:56:38
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

   /**
    * 比较两个日期
    * @param s
    * @param e
    * @return
    * @author 沈叶峰 
    * @date 2017年1月20日 上午11:56:52
    */
    public static boolean compareDate(String s, String e) {
        return !(fomatDate(s) == null || fomatDate(e) == null) && fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 字符yyyy-MM-dd格式转日期
     * @param date
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午11:57:19
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否是日期格式
     * @param s
     * @return
     * @author 沈叶峰 
     * @date 2017年1月20日 上午11:57:36
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
        } catch (Exception e) {
            return 0;
        }
    }

     /**
      * 时间相减得到天数
      * @param beginDateStr
      * @param endDateStr
      * @return
      * @author 沈叶峰 
      * @date 2017年1月20日 上午11:57:53
      */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert beginDate != null;
        assert endDate != null;
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);

        return day;
    }

     
 
    @SuppressWarnings("deprecation")
	public static Date getNowDate() {
        return dateFormat(new Date().toLocaleString(), DATE_FORMAT_YMD);
    }

 
    public static String getNowStringDate() {
        return dateFormat(new Date(), "yyyy-MM-dd");
    }

    public static String getWeekOfDate(Date date) {
        if (date == null)
            return null;
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

  
    public static String dateFormat(Date date, String dateFormat) {
        if (date == null)
            return null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            return format.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

     /**
      * 格式化时间到毫秒级
      * @param time
      * @param dateFormat
      * @return
      * @author 沈叶峰 
      * @date 2017年1月20日 下午12:10:29
      */
    public static String longDateFormat(Long time, String dateFormat) {
   
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.format(new Date(time));
            
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }
 
    public static Date dateFormat(String date, String dateFormat) {
        if (StringUtil.isEmpty(date))
            return null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(date);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }
 
    public static Date dateAdd(int interval, Date date, int num) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (interval) {
        case DATE_INTERVAL_DAY:
            calendar.add(Calendar.DATE, num);
            break;
        case DATE_INTERVAL_WEEK:
            calendar.add(Calendar.WEEK_OF_MONTH, num);
            break;
        case DATE_INTERVAL_MONTH:
            calendar.add(Calendar.MONTH, num);
            break;
        case DATE_INTERVAL_YEAR:
            calendar.add(Calendar.YEAR, num);
            break;
        case DATE_INTERVAL_HOUR:
            calendar.add(Calendar.HOUR, num);
            break;
        case DATE_INTERVAL_MINUTE:
            calendar.add(Calendar.MINUTE, num);
            break;
        case DATE_INTERVAL_SECOND:
            calendar.add(Calendar.SECOND, num);
            break;
        default:
        }
        return calendar.getTime();
    }

 
    public static double getDateDiff(int interval, Date date, Date compare) {
        if (date == null || compare == null)
            return 0;
        double result = 0;
        double time;
        Calendar calendar;
        switch (interval) {
        case DATE_INTERVAL_DAY:
            time = date.getTime() - compare.getTime();
            result = time / 1000 / 60 / 60 / 24;
            break;
        case DATE_INTERVAL_HOUR:
            time = date.getTime() - compare.getTime();
            result = time / 1000 / 60 / 60;
            break;
        case DATE_INTERVAL_MINUTE:
            time = date.getTime() / 1000 / 60;
            result = time - compare.getTime() / 1000 / 60;
            break;
        case DATE_INTERVAL_MONTH:
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            time = calendar.get(Calendar.YEAR) * 12;
            calendar.setTime(compare);
            time -= calendar.get(Calendar.YEAR) * 12;
            calendar.setTime(date);
            time += calendar.get(Calendar.MONTH);
            calendar.setTime(compare);
            result = time - calendar.get(Calendar.MONTH);
            break;
        case DATE_INTERVAL_SECOND:
            time = date.getTime() - compare.getTime();
            result = time / 1000;
            break;
        case DATE_INTERVAL_WEEK:
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            time = calendar.get(Calendar.YEAR) * 52;
            calendar.setTime(compare);
            time -= calendar.get(Calendar.YEAR) * 52;
            calendar.setTime(date);
            time += calendar.get(Calendar.WEEK_OF_YEAR);
            calendar.setTime(compare);
            result = time - calendar.get(Calendar.WEEK_OF_YEAR);
            break;
        case DATE_INTERVAL_YEAR:
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            time = calendar.get(Calendar.YEAR);
            calendar.setTime(compare);
            result = time - (double) calendar.get(Calendar.YEAR);
            break;
        default:
            break;
        }
        return new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

 
    public static String getDateBetween(Integer level, Date date, Date compare) {
        if (date == null || compare == null)
            return null;
        long s = new BigDecimal(getDateDiff(DATE_INTERVAL_SECOND, date, compare)).setScale(2, BigDecimal.ROUND_HALF_UP).longValue();
        int ss = 1;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = s / dd;
        long hour = (s - day * dd) / hh;
        long minute = (s - day * dd - hour * hh) / mi;
        long second = (s - day * dd - hour * hh - minute * mi) / ss;
        String flag = (day < 0 || hour < 0 || minute < 0 || second < 0) ? "-" : "";
        day = Math.abs(day);
        hour = Math.abs(hour);
        minute = Math.abs(minute);
        second = Math.abs(second);
        StringBuilder result = new StringBuilder(flag);
        switch (level) {
        case 1:
            if (day != 0)
                result.append(day).append("天");
            break;
        case 2:
            if (day != 0)
                result.append(day).append("天");
            if (hour != 0)
                result.append(hour).append("小时");
            break;
        case 3:
            if (day != 0)
                result.append(day).append("天");
            if (hour != 0)
                result.append(hour).append("小时");
            if (minute != 0)
                result.append(minute).append("分");
            break;
        case 4:
            if (day != 0)
                result.append(day).append("天");
            if (hour != 0)
                result.append(hour).append("小时");
            if (minute != 0)
                result.append(minute).append("分");
            if (second != 0)
                result.append(second).append("秒");
            break;
        default:
            break;
        }
        return result.toString();
    }

  
    public static boolean isToday(Date date) {
        return date != null && getNowStringDate().equals(dateFormat(date, "yyyy-MM-dd"));
    }

    public static Integer getNowWeek() {
        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.WEEK_OF_YEAR);
        int year = c.get(Calendar.YEAR);
        return year * 100 + i;
    }

    public static Integer getIntNowTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static int getTodayBegin() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return (int) (todayStart.getTime().getTime() / 1000);
    }

    public static int getTodayEnd() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return (int) (todayEnd.getTime().getTime() / 1000);
    }
     


}