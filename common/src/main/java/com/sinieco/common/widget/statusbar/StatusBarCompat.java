package com.sinieco.common.widget.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.ColorInt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 状态栏修改，可实现沉侵式和颜色填充两种方式，
 * 对不同的系统版本进行兼容。
 * Created by LiuHe on 2018/4/11.
 */

public class StatusBarCompat {

    static IStatusBar IMPL;

    static {
        // 对6.0及以上的处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IMPL = new StatusBarMImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !isEMUI()) {
            IMPL = new StatusBarLollipopImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IMPL = new StatusBarKitkatImpl();
        } else {
            IMPL = new IStatusBar() {
                @Override
                public void setColor(Window window, @ColorInt int color) {

                }

                @Override
                public void setTransparent(Window window, boolean lightStatusBar) {

                }
            };
        }
    }

    public static void setColor(Activity activity, @ColorInt int color) {
        boolean isLightColor = isLightColor(color);
        setStatusBarColor(activity, color, isLightColor);
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        boolean isLightColor = isLightColor(color);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            if (isLightColor) {
                color = Color.parseColor("#3C3F41");
            }
        }
        setStatusBarColor(activity, color, isLightColor);
    }

    public static void setOverlay(Activity activity, boolean lightStatusBar) {
        IMPL.setTransparent(activity.getWindow(), lightStatusBar);
    }

    private static void setStatusBarColor(Activity activity, @ColorInt int color, boolean lightStatusBar) {
        IMPL.setColor(activity.getWindow(), color);
        LightStatusBarCompat.setLightStatusBar(activity.getWindow(), lightStatusBar);
    }

    public static boolean isLightColor(@ColorInt int color) {
        return toGrey(color) > 225;
    }

    /**
     * 把颜色转换成灰度值。
     * 代码来自 Flyme 示例代码
     */
    public static int toGrey(@ColorInt int color) {
        int blue = Color.blue(color);
        int green = Color.green(color);
        int red = Color.red(color);
        return (red * 38 + green * 75 + blue * 15) >> 7;
    }

    private static boolean isEMUI() {
        File file = new File(Environment.getRootDirectory(), "build.prop");
        if (file.exists()) {
            Properties properties = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                properties.load(fis);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return properties.containsKey("ro.build.hw_emui_api_level");
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    static void internalSetFitsSystemWindows(Window window, boolean fitSystemWindows) {
        final ViewGroup contentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        final View childView = contentView.getChildAt(0);
        if (childView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            childView.setFitsSystemWindows(fitSystemWindows);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0 ) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
