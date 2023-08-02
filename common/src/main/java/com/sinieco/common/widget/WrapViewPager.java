package com.sinieco.common.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *高度自适应ViewPage，高度依据Page中最高的页面
 * Created by LiuHe on 2018/4/13.
 */

public class WrapViewPager extends ViewPager {

    private boolean isCanScroll = true;

    public WrapViewPager(Context context) {
        this(context, null);
    }

    public WrapViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childPageHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childPage = getChildAt(i);
            childPage.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int height = childPage.getMeasuredHeight();
            if (height > childPageHeight) {
                childPageHeight = height;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childPageHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);
    }

    public void setCanScroll(boolean canScroll) {
        this.isCanScroll = canScroll;
    }
}
