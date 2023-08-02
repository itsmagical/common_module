package com.sinieco.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.sinieco.arch.util.TimeUtils;
import com.sinieco.common.R;
import com.sinieco.common.widget.wheel.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间选择器 默认可选择年月日时分秒
 * Created by LiuHe on 2018/6/23.
 */

public class TimePickerView extends PopupWindow implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    /**
     *  年选择器类型
     */
    public static final int ACCURATE_YEAR = 1;
    /**
     *  年月选择器类型
     */
    public static final int ACCURATE_MONTH = 2;
    /**
     *  年月日选择器
     */
    public static final int ACCURATE_DAY = 3;

    /**
     *  年月日时分
     */
    public static final int ACCURATE_MINUTE = 4;

    /**
     *  年月日时分秒选择器
     */
    public static final int ACCURATE_SECOND = 5;

    public static final int ACCURATE_WEEK = 6;

    /**
     *  时分选择类型
     */
    public static final int ACCURATE_ONLY_HOUR_MINUTE = 7;

    private Context mContext;

    private WheelView wvMonth;
    private WheelView wvYear;
    private WheelView wvDay;
    private WheelView wvHour;
    private WheelView wvMinute;
    private WheelView wvSecond;
    private WheelView wvWeek;
    private long currentTimeMillis;
    private Long mEndTimestamp;

    private int monthPosition;
    private int dayPosition;
    private int hourPosition;
    private int minutePosition;
    private int secondPosition;
    private int weekPosition;

    int year;
    int month;
    int day;
    int hour;
    int minute;
    int second;
    int week;
    ArrayList<String> years;
    ArrayList<String> months;
    ArrayList<String> days;
    ArrayList<String> hours;
    ArrayList<String> minutes;
    ArrayList<String> seconds;
    ArrayList<String> weeks;
    private OnTimePickerListener onTimePickerListener;
    private long timeInMillis;

    public TimePickerView(Activity context) {
        this.mContext = context;
        this.mTimeViewType = ACCURATE_SECOND;
        init();
    }

    private int mTimeViewType;
    public TimePickerView(Activity context, int timeViewType) {
        this.mContext = context;
        this.mTimeViewType = timeViewType;
        init();
    }

    public TimePickerView(Activity context, int timeViewType, Long timestamp) {
        this.mContext = context;
        this.mTimeViewType = timeViewType;
        this.mEndTimestamp = timestamp;
        init();
    }

    private View timePickerContent;
    private void init() {
        currentTimeMillis = System.currentTimeMillis();
        timeInMillis = currentTimeMillis;
        timePickerContent = LayoutInflater.from(mContext).inflate(R.layout.view_time_picker, null);
        View cancelTime = timePickerContent.findViewById(R.id.tv_cancel_time_picker);
        View positiveTime = timePickerContent.findViewById(R.id.tv_positive_time_picker);
        cancelTime.setOnClickListener(this);
        positiveTime.setOnClickListener(this);
        initTimeCalendar();
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        setContentView(timePickerContent);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //setWidth((int) (screenWidth * 0.96));
//        setHeight((int) (screenWidth * 0.96));
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setPopupWindowBackground(1f);
            }
        });
    }

    private int curYear;
    private int curMonth;
    private int curDay;
    private int curHour;
    private int curMinute;
    private int curSecond;

    private void initTimeCalendar() {
        // 获取当前时间
        Calendar cal = Calendar.getInstance();
        curYear = cal.get(Calendar.YEAR);
        curMonth = cal.get(Calendar.MONTH) + 1;
        curDay = cal.get(Calendar.DAY_OF_MONTH);
        curHour = cal.get(Calendar.HOUR_OF_DAY);
        curMinute = cal.get(Calendar.MINUTE);
        curSecond = cal.get(Calendar.SECOND);

        // 指定时间
        Calendar initCalendar = Calendar.getInstance();
        if (mEndTimestamp != null) {
            initCalendar.setTime(new Date(mEndTimestamp));
            month = initCalendar.get(Calendar.MONTH) + 1;
            day = initCalendar.get(Calendar.DAY_OF_MONTH);
            hour = initCalendar.get(Calendar.HOUR_OF_DAY);
            minute = initCalendar.get(Calendar.MINUTE);
            second = initCalendar.get(Calendar.SECOND);
        } else {
//            month = 12;
//            day = 31;
//            hour = 23;
//            minute = 59;
//            second = 59;
            month = initCalendar.get(Calendar.MONTH) + 1;
            day = initCalendar.get(Calendar.DAY_OF_MONTH);
            hour = initCalendar.get(Calendar.HOUR_OF_DAY);
            minute = initCalendar.get(Calendar.MINUTE);
            second = initCalendar.get(Calendar.SECOND);
        }
        year = initCalendar.get(Calendar.YEAR);
//        month = 12;
//        day = 31;
//        hour = 23;
//        minute = 59;
//        second = 59;
//        month = initCalendar.get(Calendar.MONTH) + 1;
//        day = initCalendar.get(Calendar.DAY_OF_MONTH);
//        hour = initCalendar.get(Calendar.HOUR_OF_DAY);
//        minute = initCalendar.get(Calendar.MINUTE);
//        second = initCalendar.get(Calendar.SECOND);
        week = initCalendar.get(Calendar.WEEK_OF_YEAR);
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        seconds = new ArrayList<>();
        weeks = new ArrayList<>();
        filterTimeView();
    }

    private void filterTimeView() {
        if (mTimeViewType >= ACCURATE_YEAR) {
            initYear();
        }
        if (mTimeViewType >= ACCURATE_MONTH && mTimeViewType != ACCURATE_WEEK) {
            initMonth();
        }
        if (mTimeViewType >= ACCURATE_DAY && mTimeViewType != ACCURATE_WEEK) {
            initDay();
        }

        if (mTimeViewType >= ACCURATE_MINUTE && mTimeViewType != ACCURATE_WEEK) {
            initHM();
        }

        if (mTimeViewType == ACCURATE_SECOND && mTimeViewType != ACCURATE_WEEK) {
            initHMS();
        }
        if (mTimeViewType == ACCURATE_WEEK) {
            initWeek();
        }
        if (mTimeViewType == ACCURATE_ONLY_HOUR_MINUTE) {
            initOnlyHM();
        }
    }

    private void initYear() {
        wvYear = (WheelView) timePickerContent.findViewById(R.id.wv_year);
        wvYear.setVisibility(View.VISIBLE);
        wvYear.setIsLoop(true);
        for (int i = 2000; i <= year; i++) {
            years.add(String.valueOf(i) + "年");
        }
        // wvYear.setItems(years, years.size() - 1);
        wvYear.setItems(years, getPosition(years, curYear + "年"));
        wvYear.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index,String item) {
                resetTime();
            }
        });
    }

    private void initMonth() {
        wvMonth = (WheelView) timePickerContent.findViewById(R.id.wv_month);
        wvMonth.setVisibility(View.VISIBLE);
        wvMonth.setIsLoop(true);
        setTimeDate(month, 0, 0, 0, 0, 0);
        // wvMonth.setItems(months, months.size() - 1);
        wvMonth.setItems(months, getPosition(months, curMonth + "月"));
        wvMonth.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                resetTime();
            }
        });
    }

    private void initDay() {
        wvDay = (WheelView) timePickerContent.findViewById(R.id.wv_day);
        wvDay.setVisibility(View.VISIBLE);
        wvDay.setIsLoop(true);
        setTimeDate(month, day, 0, 0, 0, 0);
        // wvDay.setItems(days, days.size() - 1);
        wvDay.setItems(days, getPosition(days, curDay + "日"));
        wvDay.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {
                resetTime();
            }
        });
    }

    private void initHM() {
        //小时滚轮
        wvHour = (WheelView) timePickerContent.findViewById(R.id.wv_hour);
        //分钟滚轮
        wvMinute = (WheelView) timePickerContent.findViewById(R.id.wv_minute);
        wvHour.setVisibility(View.VISIBLE);
        wvMinute.setVisibility(View.VISIBLE);
        wvHour.setIsLoop(true);
        wvMinute.setIsLoop(true);
        setTimeDate(month, day, hour, minute, 0, 0);
        // wvHour.setItems(hours,hours.size() - 1);
        // wvMinute.setItems(minutes,minutes.size() - 1);
        wvHour.setItems(hours, getPosition(hours, curHour + "时"));
        wvMinute.setItems(minutes, getPosition(minutes, curMinute + "分"));
        wvHour.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index,String item) {
                resetTime();
            }
        });
        wvMinute.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index,String item) {
                resetTime();
            }
        });
    }

    private void initHMS() {
        wvSecond = (WheelView) timePickerContent.findViewById(R.id.wv_second);
        wvSecond.setVisibility(View.VISIBLE);
        wvSecond.setIsLoop(true);
        setTimeDate(month, day, hour, minute, second, 0);
        // wvSecond.setItems(seconds, seconds.size() - 1);
        wvSecond.setItems(seconds, getPosition(seconds, curSecond + "秒"));
    }

    private void initOnlyHM() {
        wvYear.setVisibility(View.GONE);
        wvMonth.setVisibility(View.GONE);
        wvDay.setVisibility(View.GONE);
        wvHour.setVisibility(View.VISIBLE);
        wvMinute.setVisibility(View.VISIBLE);
        //wvSecond.setVisibility(View.GONE);
    }

    private int getPosition(List<String> items, String item) {
        int position = items.indexOf(item);
        Log.e(TAG, "getPosition: " + position );
        return position;
    }

    private void initWeek() {
        wvWeek = (WheelView) timePickerContent.findViewById(R.id.wv_week);
        wvWeek.setVisibility(View.VISIBLE);
        wvWeek.setIsLoop(true);
        setTimeDate(0, 0, 0, 0, 0, week);
        wvWeek.setItems(weeks, weeks.size() - 1);
        wvWeek.setOnItemSelectedListener(new WheelView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int selectedIndex, String item) {

            }
        });
    }

    private void initTime() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);

        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        seconds = new ArrayList<>();
        weeks = new ArrayList<>();
        for (int i = 2000; i <= year; i++) {
            years.add(String.valueOf(i) + "年");
        }
        setTimeDate(month, day, hour, minute, second, 0);
    }

    private String focusYear;
    private String focusMonth = "1月";
    private String focusDay = "1日";
    private String focusHour = "0时";
    private String focusMinute = "0分";
    private String focusSecond = "0秒";
    private String focusWeek = "1周";


    private void resetTime() {
        getFocusItem();
        getPureTime(focusYear);
        refreshTime(getPureTime(focusYear), getPureTime(focusMonth), getPureTime(focusDay),
                getPureTime(focusHour), getPureTime(focusMinute), getPureTime(focusSecond));
    }

    private String getFocusItem() {
        int yearPosition = wvYear.getSelectedPosition();
        focusYear = years.get(yearPosition);
        if (wvMonth != null) {
            monthPosition = wvMonth.getSelectedPosition();
            focusMonth = months.get(monthPosition);
        }
        if (wvDay != null) {
            dayPosition = wvDay.getSelectedPosition();
            focusDay = days.get(wvDay.getSelectedPosition());
        }
        if (wvHour != null) {
            hourPosition = wvHour.getSelectedPosition();
            focusHour = hours.get(hourPosition);
        }
        if (wvMinute != null) {
            minutePosition = wvMinute.getSelectedPosition();
            focusMinute = minutes.get(minutePosition);
        }
        if (wvSecond != null) {
            secondPosition = wvSecond.getSelectedPosition();
            focusSecond = seconds.get(secondPosition);
        }
        if (wvWeek != null) {
            weekPosition = wvWeek.getSelectedPosition();
            focusWeek = weeks.get(weekPosition);
        }
        if (mTimeViewType == ACCURATE_WEEK) {
            return getPureTime(focusYear) + "," + getPureTime(focusWeek);
        } else {
            return focusYear + focusMonth + focusDay +" "+ focusHour + focusMinute + focusSecond;
        }
    }

    private int getPureTime(String time) {
        String pureTime = time.substring(0, time.length() - 1);
        return Integer.valueOf(pureTime);
    }

    private void refreshTime(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        // calendar.set(Calendar.DAY_OF_MONTH, day);
        // 解决 存在2月30日、4月31日这样的问题
        if (day > getMaxDayForMonth(year, month)) {
            calendar.set(Calendar.DAY_OF_MONTH, getMaxDayForMonth(year, month));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        long timeInMillis = calendar.getTimeInMillis();

        String selectedYear = TimeUtils.getYear(timeInMillis);
        String selectedMonth = getFormat(timeInMillis, "MM");
        String selectedDay = TimeUtils.getDay(timeInMillis);
        String selectedHour = getFormat(timeInMillis, "HH");
        String selectedMinute = getFormat(timeInMillis, "mm");
        String selectedSecond = getFormat(timeInMillis, "ss");
        
        setDate(Integer.valueOf(selectedYear), Integer.valueOf(selectedMonth),
                Integer.valueOf(selectedDay), Integer.valueOf(selectedHour), Integer.valueOf(selectedMinute), Integer.valueOf(selectedSecond));
        // 没有设置自定义结束时间时 可选择的最大时间为当前时间
        if (mEndTimestamp == null && timeInMillis > currentTimeMillis) {
            initTime();
        }
        if (wvMonth != null) {
            wvMonth.setItems(months, monthPosition);
        }
        if (wvDay != null) {
            wvDay.setItems(days, dayPosition);
        }
        if (wvHour != null) {
            wvHour.setItems(hours, hourPosition);
        }
        if (wvMinute != null) {
            wvMinute.setItems(minutes,minutePosition);
        }
        if (wvSecond != null) {
            wvSecond.setItems(seconds, secondPosition);
        }
        if (wvWeek != null) {
            wvWeek.setItems(weeks, weekPosition);
        }
    }

    private void setTimeDate(int month, int day, int hour, int minute, int second, int week) {
        months.clear();
        for (int i = 1; i <= month; i++) {
            months.add(String.valueOf(i) + "月");
        }
        days.clear();
        for (int i = 1; i <= day; i++) {
            days.add(String.valueOf(i) + "日");
        }
        hours.clear();
        for (int i = 0; i <= hour; i++) {
            hours.add(String.valueOf(i) + "时");
        }
        minutes.clear();
        for (int i = 0; i <= minute; i++) {
            minutes.add(String.valueOf(i) + "分");
        }
        seconds.clear();
        for (int i = 0; i <= second; i++) {
            seconds.add(String.valueOf(i) + "秒");
        }
        weeks.clear();
        for (int i = 1; i <= week; i++) {
            weeks.add(String.valueOf(i) + "周");
        }
    }

    private void setDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        timeInMillis = calendar.getTimeInMillis();

        int maxMonth = 12;
        int maxHour = 23;
        int maxMinute = 59;
        int maxSecond = 59;
        int maxWeek = 52;

        if (mEndTimestamp == null) {
            if (year == Integer.valueOf(TimeUtils.getYear(currentTimeMillis)) && month != 0) {
                maxMonth = Integer.valueOf(TimeUtils.getMonthOfYear(System.currentTimeMillis()));
            }
            if (year == Integer.valueOf(TimeUtils.getYear(currentTimeMillis)) &&
                    month == Integer.valueOf(TimeUtils.getMonthOfYear(currentTimeMillis))) {
                maxDay = Integer.valueOf(TimeUtils.getDay(System.currentTimeMillis()));
            }
            if (year == Integer.valueOf(TimeUtils.getYear(currentTimeMillis)) &&
                    month == Integer.valueOf(TimeUtils.getMonthOfYear(currentTimeMillis))
                    && day == Integer.valueOf(getFormat(currentTimeMillis, "dd"))) {
                maxHour = Integer.valueOf(getFormat(currentTimeMillis, "HH"));
            }

            if (year == Integer.valueOf(TimeUtils.getYear(currentTimeMillis)) &&
                    month == Integer.valueOf(TimeUtils.getMonthOfYear(currentTimeMillis)) &&
                    day == Integer.valueOf(getFormat(currentTimeMillis, "dd")) &&
                    hour == Integer.valueOf(getFormat(currentTimeMillis, "HH"))) {
                maxMinute = Integer.valueOf(getFormat(currentTimeMillis, "mm"));
                Log.e(TAG, "setDate: " + maxMinute );
            }
            if (year == Integer.valueOf(TimeUtils.getYear(currentTimeMillis)) &&
                    month == Integer.valueOf(TimeUtils.getMonthOfYear(currentTimeMillis)) &&
                    day == Integer.valueOf(getFormat(currentTimeMillis, "dd")) &&
                    hour == Integer.valueOf(getFormat(currentTimeMillis, "HH")) &&
                    minute == Integer.valueOf(getFormat(currentTimeMillis, "mm"))) {
                maxSecond = Integer.valueOf(getFormat(currentTimeMillis, "ss"));
            }
            if (year == Integer.valueOf(TimeUtils.getYear(currentTimeMillis))) {
                Calendar instance = Calendar.getInstance();
                maxWeek = instance.get(Calendar.WEEK_OF_YEAR);
            }
        }
        setTimeDate(maxMonth, maxDay, maxHour, maxMinute, maxSecond, maxWeek);
    }

    private int getMaxDayForMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    public String getFormat(long time, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date(time);
        return  format.format(date);
    }

    public void showTimePickerView() {
        setPopupWindowBackground(0.5f);
        showAtLocation(((Activity)mContext).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    public void showFromBottom() {
        setPopupWindowBackground(0.5f);
        setAnimationStyle(R.style.pop_from_bottom_anim);
        showAtLocation(((Activity)mContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }



    private void setPopupWindowBackground(float alpha) {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow()
                .getAttributes();
        lp.alpha = alpha;
        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel_time_picker) {
            dismiss();
        }
        if (v.getId() == R.id.tv_positive_time_picker) {
            if (onTimePickerListener != null) {
                String focusTime = getFocusItem();
                // Log.e(TAG, "onClick: " + focusTime);
                if (mTimeViewType != ACCURATE_WEEK) {
                    // 2018年7月11日 0时0分0秒;
                    long selectedTime = TimeUtils.timeDescToTimestamp(focusTime, "yyyy年MM月dd日 HH时mm分ss秒");
                    onTimePickerListener.OnSelectedTimeStamp(selectedTime);
                } else {
                    // 选择周
                    String[] yearWeek = focusTime.split(",");
                    if (yearWeek.length == 2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, Integer.valueOf(yearWeek[0]));
                        calendar.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(yearWeek[1]));
                        calendar.setFirstDayOfWeek(Calendar.MONDAY);
                        long timeInMillis = calendar.getTimeInMillis();
                        onTimePickerListener.OnSelectedTimeStamp(timeInMillis);
                    }
                }
                dismiss();
            }
        }
    }

//        public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_cancel_time_picker:
//                dismiss();
//                break;
//            case R.id.tv_positive_time_picker:
//                if (onTimePickerListener != null) {
//                    String focusTime = getFocusItem();
//                    Log.e(TAG, "onClick: " + focusTime);
//                    // 2018年7月11日 0时0分0秒;
//                    long selectedTime = TimeUtils.timeDescToTimestamp(focusTime, "yyyy年MM月dd日 HH时mm分ss秒");
//                    onTimePickerListener.OnSelectedTimeStamp(selectedTime);
//                    //onTimePickerListener.OnSelectedTimeStamp(timeInMillis);
//                    dismiss();
//                }
//                break;
//        }
//    }

    public interface OnTimePickerListener {
        void OnSelectedTimeStamp(Long timeStamp);
    }

    public void setOnTimePickerListener(OnTimePickerListener listener) {
        onTimePickerListener = listener;
    }

}
