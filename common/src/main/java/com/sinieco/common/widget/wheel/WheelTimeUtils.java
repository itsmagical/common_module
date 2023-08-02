package com.sinieco.common.widget.wheel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/9/20.
 */

public class WheelTimeUtils {

    public static String getYear(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static String getMonthOfYear(long time){
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Date date = new Date(time);
        return  format.format(date);
    }

    public static String getDay(long time){
        SimpleDateFormat format = new SimpleDateFormat("dd");
        Date date = new Date(time);
        return  format.format(date);
    }
}
