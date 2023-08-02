package com.sinieco.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * Created by LiuHe on 2018/9/10.
 */

public class TimeUtils {

    public static String formatTime(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String formatMunite(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public static String formatMonth(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }

    public static String formatDay(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String formatDayWithPoint(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(date);
    }

    public static String formatYearSimple(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat.format(date);
    }

    public static String formatMonthSimple(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        return dateFormat.format(date);
    }

    public static String formatDaySimple(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    public static String formatYearDes(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年");
        return dateFormat.format(date);
    }

    public static String formatMonthDes(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
        return dateFormat.format(date);
    }

    public static String formatDayDes(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return dateFormat.format(date);
    }

    public static String formatMonth1st() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(calendar.getTime());
    }

    public static String formatMonth1stWithPoint() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(calendar.getTime());
    }

    public static String formatDayBySkew(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static String formatTwoBitYearBySkew(long time){
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static String formatFileNameTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    /**
     *  获取指定时间戳的上一年
     */
    public static long getPreYear(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTimeInMillis();
    }
    /**
     *  获取指定时间戳的下一年
     */
    public static long getNextYear(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, +1);
        return calendar.getTimeInMillis();
    }

    public static long getAfterTwoYear() {
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, +2);
        return calendar.getTimeInMillis();
    }

    /**
     *  获取指定时间戳的上一个月
     */
    public static long getPreMonth(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTimeInMillis();
    }
    /**
     *  获取指定时间戳的下一个月
     */
    public static long getNextMonth(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, +1);
        return calendar.getTimeInMillis();
    }

    /**
     *  获取指定时间戳的上一天
     */
    public static long getPreDay(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTimeInMillis();
    }
    /**
     *  获取指定时间戳的下一天
     */
    public static long getNextDay(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar.getTimeInMillis();
    }

    public static long getPreWeek(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        return calendar.getTimeInMillis();
    }

    public static long getNextWeek(long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +7);
        return calendar.getTimeInMillis();
    }

    public static String getStartTimeByDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return  format.format(date) + " 00:00:00";
    }

    public static String getEndTimeByDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return  format.format(date) + " 23:59:59";
    }

    public static String getStartTimeByMonth(long time) {
        Date date = new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String monthStart = format.format(c.getTime())+" 00:00:00";
        return monthStart;
    }

    public static long getEndTimeStampByDay() {
        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTimeInMillis();
    }

    public static String getEndTimeByMonth(long time) {
        Date date = new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String monthEnd = format.format(c.getTime())+" 23:59:59";
        return monthEnd;
    }

    public static String getStartTimeByYear(long time) {
        Date date = new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(c.get(Calendar.YEAR) ,0, 1);//开始时间日期
        String yearStart = format.format(c.getTime())+" 00:00:00";
        return yearStart;
    }

    public static String getEndTimeByYear(long time) {
        Date date = new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(c.get(Calendar.YEAR) ,11, c.getActualMaximum(Calendar.DAY_OF_MONTH));//结束日期
        String yearEnd = format.format(c.getTime())+" 23:59:59";
        return yearEnd;
    }


    public static String getYear(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static String getYearDesc(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static String getMonthOfYear(long time){
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static String getMonthOfYearDes(long time){
        SimpleDateFormat format = new SimpleDateFormat("M月");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static Long getPreSegmentYear(long time) {
        Date date = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -1);
        c.add(Calendar.MONTH, +1);
        return c.getTimeInMillis();
    }

    public static String getDay(long time){
        SimpleDateFormat format = new SimpleDateFormat("dd");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static int getWeekOfYear(long time) {
        Calendar calendar = Calendar.getInstance();
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date(time));
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static long timeDescToTimestamp(String timeDesc, String timePattern) {
        Date date = timeDescToDate(timeDesc, timePattern);
        long timestamp = 0;
        if (date != null) {
            return date.getTime();
        }
        return timestamp;
    }

    private static Date timeDescToDate(String timeDesc, String timePattern) {
        SimpleDateFormat format = new SimpleDateFormat(timePattern);
        try {
            return format.parse(timeDesc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
