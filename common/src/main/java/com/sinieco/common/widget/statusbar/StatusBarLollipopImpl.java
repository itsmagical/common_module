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
 * Description: 对5.0的处理
 */

public class StatusBarLollipopImpl implements IStatusBar {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setColor(Window window, @ColorInt int color) {
        if (StatusBarCompat.isLightColor(color)) {
            setStatusBar(window, Color.parseColor("#66000000"));
        } else {
            setStatusBar(window, color);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setTransparent(Window window, boolean lightStatusBar) {
        setStatusBar(window, Color.TRANSPARENT);
        if (lightStatusBar) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar(Window window, int color) {
        // 取消设置透明状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 设置状态栏为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // 设置状态栏颜色
        window.setStatusBarColor(color);
    }
}
