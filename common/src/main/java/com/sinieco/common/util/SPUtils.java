package com.sinieco.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.sinieco.arch.util.Utils;

/**
 *
 * Created by LiuHe on 2018/9/10.
 */

public class SPUtils {

    private static final String SP_NAME = "sharedContext";

    public static void put(String key, Object value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        }
        edit.commit();
    }

    public static Object get(String key, Object defaultValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        }
        return null;
    }
}
