package com.sinieco.common.widget.navigation;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;

import java.util.Collection;

/**
 *  Navigation 工具类
 * Created by admin on 2019/5/25.
 */
class Utils {

    static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    static boolean isLayoutParamsSpecialValue(int paramsValue) {
        return ViewGroup.LayoutParams.MATCH_PARENT == paramsValue
                || ViewGroup.LayoutParams.WRAP_CONTENT == paramsValue;
    }

    static boolean isNotEmpty(Collection collection) {
        return null != collection && collection.size() > 0;
    }

    static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }

}
