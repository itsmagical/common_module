package com.sinieco.common.util;

import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuHe on 2019/6/4.
 */

public class BundleUtils {

    private final Bundle mBundle;

    private BundleUtils() {
        mBundle = new Bundle();
    }

    public static BundleUtils Build() {
        return new BundleUtils();
    }

    public BundleUtils putInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public BundleUtils putString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public BundleUtils putLong(String key, Long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public BundleUtils putFloat(String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public BundleUtils putDouble(String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public BundleUtils putBoolean(String key, Boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public BundleUtils putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public BundleUtils putStringArrayList(String key, ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

    public Bundle create() {
        return mBundle;
    }

}
