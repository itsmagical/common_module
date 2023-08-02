package com.sinieco.common.util;

import android.text.TextUtils;

public class ValueTransUtil {

    public static int intTrans(String value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Integer.valueOf(value);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public static double doubleTrans(String value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Double.valueOf(value);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public static float floatsTrans(String value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Float.valueOf(value);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public static long longTrans(String value) {
        try {
            if (!TextUtils.isEmpty(value)) {
                return Long.valueOf(value);
            }
        } catch (Exception e) {

        }
        return 0;
    }


}
