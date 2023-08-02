package com.sinieco.common.widget.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 *
 * Created by LiuHe on 2018/9/5.
 */

class PopWindowController {
    private PopViewHelper helper;

    PopWindowController(CommonWindow commonWindow) {

    }

    private void setHelper(PopViewHelper helper) {
        this.helper = helper;
    }

    public <T extends View> T getView(@IdRes int idRes) {
       return helper.getView(idRes);
    }

    public void setVisibility(int idRes, int visibility) {
        View view = helper.getView(idRes);
        view.setVisibility(visibility);
    }

    public void setOnClickListener(@IdRes int idRes, View.OnClickListener listener) {
        View view = helper.getView(idRes);
        view.setOnClickListener(listener);
    }

    static class PopWindowParams {
        public Context context;
        public @LayoutRes
        int mContentViewRes;
        public View mContentView;
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        public boolean mFocusable = true;

        public PopWindowParams(Context context) {
            this.context = context;
        }

        public void apply(CommonWindow commonWindow) {
            PopViewHelper popViewHelper = null;
            if (mContentViewRes != 0) {
                popViewHelper = new PopViewHelper(context, mContentViewRes);
            }
            if (mContentView != null) {
                popViewHelper = new PopViewHelper(context);
                popViewHelper.setContentView(mContentView);
            }
            if (popViewHelper == null) {
                throw new NullPointerException("没有设置布局");
            }
            commonWindow.getController().setHelper(popViewHelper);
            commonWindow.setContentView(popViewHelper.getContentView());
            commonWindow.setOutsideTouchable(false);
            commonWindow.setWidth(mWidth);
            commonWindow.setHeight(mHeight);
            commonWindow.setFocusable(mFocusable);
        }
    }
}
