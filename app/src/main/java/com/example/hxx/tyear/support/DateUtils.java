package com.example.hxx.tyear.support;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
//时间
 /*SimpleDateFormat转换时间，12,24时间格式*/
public class DateUtils {

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_ZH = "yyyy年MM月dd日";
    public static final String FORMAT_ACTIVITY_TIME = "yyyy.MM.dd";
    public static final String FORMAT_LOTTERY_TIME = "MM/dd HH:mm";

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);//设置格式
        return sdf.format(new Date());//输出新时间
    }

    public static String formatDate(long time) {
        return format(FORMAT_DATE, time);
    }

    public static String formatDateZh(long time) {
        return format(FORMAT_DATE_ZH, time);
    }

    public static String formatDate(Date date) {//渲染时间格式
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        return format.format(date);
    }

    public static String formatDateZh(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_ZH);
        return format.format(date);
    }

    /**
     * 格式化时间
     *
     * @param inFormat
     * @param time
     * @return
     */
    public static String format(String inFormat, long time) {
        SimpleDateFormat format = new SimpleDateFormat(inFormat);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 获取更新日期
     *
     * @return
     */
    public static String getRefreshDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 获取普通时间
     *
     * @param time
     * @return
     */
    public static String getCommonDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String getFriendlyTime(long time) {
        Date date = new Date(time);
        if (date == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(date);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = date.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(date);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
