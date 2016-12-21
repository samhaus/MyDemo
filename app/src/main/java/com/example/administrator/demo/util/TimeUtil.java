package com.example.administrator.demo.util;

import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author lsh 2015-08-04
 * @version 1.0
 *          时间相关的处理
 */
public class TimeUtil {
    /**
     * String类型毫秒数long转换成日期 :分割
     *
     * @param lo 毫秒数
     * @return String
     */
    public static String stringToDate(long lo) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

        return sd.format(date);
    }

    /**
     * String类型毫秒数long转换成日期 :分割
     *
     * @param lo 毫秒数
     * @return String
     */
    public static String stringToDateNoSS(long lo) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        return sd.format(date);
    }


    /**
     * String类型毫秒数long转换成日期 :不分割
     *
     * @param lo 毫秒数
     * @return String
     */
    public static String stringToDateNodiv(long lo) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        return sd.format(date);
    }


    /**
     * String类型毫秒数long转换成日期 -分割
     * xx-xx-xx
     *
     * @param lo 毫秒数
     * @return String
     */
    public static String getDate(long lo) {
//        if(lo<=0)
//            return "";
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("yyyy年MM月dd日");

        return sd.format(date);
    }

    /**
     * String类型毫秒数long转换成日期 -分割
     * xx-xx-xx
     *
     * @param lo 毫秒数
     * @return String
     */
    public static String getDate(long lo, String format) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat(format);

        return sd.format(date);
    }

    /*
    在String的时间插入字符
    type1  201608
    type2  20160808
     */
    public static String devideTime(String mTime, int type) {
        int position1 = 4, position2 = 6, position3 = 8;
        String year = "年", month = "月", day = "日";
        if (type == 1) {
            mTime = mTime.substring(0, position1) + year + mTime.substring(position1, position2) + month;
        } else if (type == 2) {
            mTime = mTime.substring(0, position1) + year + mTime.substring(position1, position2)
                    + month + mTime.substring(position2, position3) + day;
        }

        return mTime;
    }

    public static String getMonthAndDay(long lo) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("MM月dd日");

        return sd.format(date);
    }

    public static String getTimeWithLine(long lo) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("yyyy-MM-dd");

        return sd.format(date);
    }


    public static String getYearMonthDay(long lo) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("yyyy年MM月dd日");

        return sd.format(date);
    }

    public static String getYear(long lo) {
        long time = lo;
        SimpleDateFormat sd;
        Date date = new Date(time);

        sd = new SimpleDateFormat("yyyy");

        return sd.format(date);
    }

    /**
     * 将所给日期转换为毫秒,String类型的，所给日期的格式必须为："yyyy:MM:dd HH:mm:ss"
     * EXIF信息中，为标准的此格式
     *
     * @param format
     * @return
     */
    public static long getTime(String format) { //设定时间的模板

        SimpleDateFormat sdf = new SimpleDateFormat(format); //得到指定模范的时间
        Date d = null;
        try {
            d = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d != null ? d.getTime() : 0;
    }


    /***
     * 计算获得经验
     * @param oldtTime 早一点的时间
     * @param newTime  最新时间
     * **/
    public static String getExperience(long oldtTime,long newTime){
        long day = 0;
        long hour = 0;
        long diff=newTime-oldtTime ;
        long year=0;
        year=diff/(24 * 60 * 60 * 1000*365);
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        if(year!=0){
            return year+"年";
        }
        if(day!=0){
            return day+"天";
        }
        if(hour!=0){
            return hour+"小时";
        }
        return "刚刚";
    }


    /**
     * 实现将初始日期时间2012年07月02日  拆分成年 月 日,并赋值给calendar
     *
     * @param initDateTime 初始日期时间值 字符串型
     * @return Calendar
     */
    public static Calendar getCalendar(long initDateTime) {
        String date = getDate(initDateTime);

        String[] array = date.split("-");

        String year = array[0];
        String month = array[1];
        String day = array[2];


        Calendar calendar = Calendar.getInstance();


        int currentYear = Integer.valueOf(year.trim());
        int currentMonth = Integer.valueOf(month.trim()) - 1;
        int currentDay = Integer.valueOf(day.trim());

        calendar.set(currentYear, currentMonth, currentDay);
        return calendar;
    }


    /**
     * 将时间选择器上的年月日时间转换为Long型
     *
     * @return
     */
    public static long getCalendarTime(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth());
        return calendar.getTime().getTime();
    }

    /**
     * 获取北京时间当前时间，并将其转化为Long型，用于方便和别的推送比较
     *
     * @return
     */
    public static long getCurrentTime() {
        return new Date().getTime();
    }

    /**
     * 判断用户的设备时区是否为东八区（中国）2014年7月31日
     *
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 旧时区到新时区的时间转换
     *
     * @param date
     * @param oldZone
     * @param newZone
     * @return
     */
    public static Date transformTime(Date date, TimeZone oldZone,
                                     TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}