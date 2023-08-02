package com.sinieco.common.widget.statusbar;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

/**
 * Created by LiuHe on 2018/4/11.
 * Description: 对6.0及以上做兼容处理
 */

public class StatusBarMImpl implements IStatusBar {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void setColor(Window window, @ColorInt int color) {
        setStatusBar(window, color);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void setTransparent(Window window, boolean lightStatusBar) {
        setStatusBar(window, Color.TRANSPARENT);
        if (lightStatusBar) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setStatusBar(Window window, @ColorInt int color) {
        // 取消设置透明状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置状态栏为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        // 设置状态栏颜色
        window.setStatusBarColor(color);
        // 去掉状态栏下的windowContentOverlay
        View overlay = window.findViewById(android.R.id.content);
        if (overlay != null) {
           // overlay.setForeground(null);
        }
    }


    /**
     *  View.SYSTEM_UI_FLAG_STATE: 显示状态栏 activity不全屏显示
     *  View.SYSTEM_UI_FLAG_FULLSCREEN: 全屏显示且状态栏会被隐藏
     *  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN: 全屏显示activity布局，状态栏不会被隐藏，activity顶端布局会被状态栏栏遮住
     */

}
