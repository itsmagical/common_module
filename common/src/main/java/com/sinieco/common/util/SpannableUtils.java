package com.sinieco.common.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by LiuHe on 2018/9/18.
 */

public class SpannableUtils {


    public static SpannableString wholeColor(String wholeStr, int color) {
        SpannableString span = new SpannableString(wholeStr);
        span.setSpan(new ForegroundColorSpan(color), 0, wholeStr.length(), 0);
        return span;
    }

    public static SpannableString lastWord(int stringRes, int color) {
        return lastWord(ResUtils.getString(stringRes), color);
    }

    /**
     * 最后一个字符的颜色
     */
    public static SpannableString lastWord(String pattern, int color) {
        SpannableString span = new SpannableString(pattern);
        span.setSpan(new ForegroundColorSpan(color), pattern.length() - 1, pattern.length(), 0);
        return span;
    }

    public static SpannableString matchColor(String wholeStr, String keyword, int color) {
        //Pattern.matches(keyword, wholeStr);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(wholeStr);
//        int index = 0;
//        if (matcher.find()) {
//            int index = matcher.start();
//        }
        // int index = wholeStr.indexOf(keyword);
        int index = wholeStr.lastIndexOf(keyword);
        SpannableString span = new SpannableString(wholeStr);
        span.setSpan(new ForegroundColorSpan(color), index, index + keyword.length(), 0);
        return span;

    }

    public static SpannableString matchColorAndSize(String wholeStr, String keyword, int color, float textSize) {
        int startCursor = wholeStr.indexOf(keyword);
        int lastCursor = wholeStr.lastIndexOf(keyword);
        SpannableString span = new SpannableString(wholeStr);
        span.setSpan(new ForegroundColorSpan(color), startCursor, startCursor + keyword.length(), 0);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(textSize);
        span.setSpan(sizeSpan, startCursor, startCursor + keyword.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        StyleSpan boldSpan =  new StyleSpan(Typeface.BOLD);
        span.setSpan(boldSpan, startCursor, startCursor + keyword.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static SpannableString generateBr(String aboveDesc, String belowDesc, int aboveColor, int belowColor) {
        int aboveLength = aboveDesc.length() + 1;
        int belowLength = belowDesc.length();
        SpannableString span = new SpannableString(aboveDesc + "\n" + belowDesc);
        span.setSpan(new ForegroundColorSpan(aboveColor), 0, aboveLength, 0);
        span.setSpan(new ForegroundColorSpan(belowColor), aboveLength, aboveLength + belowLength, 0);
        return span;
    }




}
