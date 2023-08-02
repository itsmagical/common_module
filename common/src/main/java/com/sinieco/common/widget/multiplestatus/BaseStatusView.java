package com.sinieco.common.widget.multiplestatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;

/**
 * 基本状态布局
 * 可使用状态布局生命周期，以及重试回调
 */
public abstract class BaseStatusView extends LinearLayout {

    public BaseStatusView(Context context, @LayoutRes int layoutRes) {
       this(context, layoutRes, null);
    }

    public BaseStatusView(Context context, @LayoutRes int layoutRes, StatusHandler handler) {
        super(context);
        LayoutInflater.from(getContext()).inflate(layoutRes, this, true);
    }

    /**
     * 状态布局将要显示
     */
    public abstract void onAppear();

    /**
     * 状态布局将要被移除隐藏
     */
    public abstract void onDisappear();

}
