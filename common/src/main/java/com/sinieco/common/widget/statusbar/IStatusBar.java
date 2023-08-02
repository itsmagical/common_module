package com.sinieco.common.widget.statusbar;

import android.view.Window;

import androidx.annotation.ColorInt;

/**
 * Created by LiuHe on 2018/4/11.
 * Description: 统一接口
 */

public interface IStatusBar {

    /**
     *  设置状态栏颜色
     * @param window
     * @param color 需要设置的颜色
     */
    public void setColor(Window window, @ColorInt int color);


    public void setTransparent(Window window, boolean lightStatusBar);

}
