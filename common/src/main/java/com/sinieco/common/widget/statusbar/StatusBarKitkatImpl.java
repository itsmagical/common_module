package com.sinieco.common.widget.statusbar;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;

/**
 * Created by LiuHe on 2018/4/11.
 * Description: 对4.4做兼容处理
 */

public class StatusBarKitkatImpl implements IStatusBar {

    private static final String FAKE_STATUS_BAR_VIEW = "fakestatusbarview";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void setColor(Window window, @ColorInt int color) {
        // 清除透明状态栏 FLAG_TRANSLUCENT_STATUS flag在5.0以下是沉侵式全透明的效果，以上是沉侵半透明效果
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = decorViewGroup.findViewWithTag(FAKE_STATUS_BAR_VIEW);
        if (statusBarView == null) {
            statusBarView = new StatusBarView(window.getContext());
            statusBarView.setTag(FAKE_STATUS_BAR_VIEW);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.TOP;
            statusBarView.setLayoutParams(layoutParams);
            decorViewGroup.addView(statusBarView);
        }
        statusBarView.setBackgroundColor(color);
        StatusBarCompat.internalSetFitsSystemWindows(window, true);
    }

    @Override
    public void setTransparent(Window window, boolean lightStatusBar) {
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = decorViewGroup.findViewWithTag(FAKE_STATUS_BAR_VIEW);
        if (statusBarView != null) {
            decorViewGroup.removeView(statusBarView);
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        StatusBarCompat.internalSetFitsSystemWindows(window, false);
    }
}
