package com.sinieco.common.util;

import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

import com.sinieco.arch.util.Utils;

/**
 *
 * Created by LiuHe on 2018/9/27.
 */

public class ResUtils {

    public static String getString(@StringRes int stringRes) {
        return Utils.getContext().getString(stringRes);
    }

    public static int getColor(@ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Utils.getContext().getColor(colorRes);
        } else {
            return Utils.getContext().getResources().getColor(colorRes);
        }
    }

    public static Drawable getDrawable(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Utils.getContext().getDrawable(resId);
        } else {
            return Utils.getContext().getResources().getDrawable(resId);
        }
    }

    public static String[] getStringArray(int stringArrayRes) {
        return Utils.getContext().getResources().getStringArray(stringArrayRes);
    }

    public static float getDimen(int dimenRes) {
        return Utils.getContext().getResources().getDimension(dimenRes);
    }


}
