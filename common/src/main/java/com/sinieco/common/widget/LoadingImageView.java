package com.sinieco.common.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sinieco.common.R;

/**
 *
 * Created by LiuHe on 2018/9/11.
 */

public class LoadingImageView extends ImageView {
    public LoadingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.drawable.spinner);
        start();
    }

    private void start() {
        AnimationDrawable loadingDrawable = (AnimationDrawable) getBackground();
        loadingDrawable.start();
    }



}
