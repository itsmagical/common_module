package com.sinieco.common.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 *
 * Created by LiuHe on 2018/9/5.
 */

public class CommonWindow extends PopupWindow {

    private Context mContext;
    private final PopWindowController controller;

    private CommonWindow(Context context) {
        this.mContext = context;
        controller = new PopWindowController(this);
    }

    public PopWindowController getController() {
        return controller;
    }

    public <T extends View> T getView(@IdRes int idRes) {
        return controller.getView(idRes);
    }

    public void setVisibility(int idRes, int visibility) {
        controller.setVisibility(idRes, visibility);
    }

    public void setOnClickListener(@IdRes int idRes, View.OnClickListener listener) {
        controller.setOnClickListener(idRes, listener);
    }

    public static class Builder {
        private final Context context;
        private final PopWindowController.PopWindowParams p;

        public Builder(Context context) {
            this.context = context;
            p = new PopWindowController.PopWindowParams(context);
        }

        public Builder setContentView(@LayoutRes int contentViewRes) {
            p.mContentViewRes = contentViewRes;
            return this;
        }

        public Builder setContentView(View view) {
            p.mContentView = view;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            p.mFocusable = focusable;
            return this;
        }

        public Builder setContentWidth(int width) {
            p.mWidth = width;
            return this;
        }

        public Builder setContentHeight(int height) {
            p.mHeight = height;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            p.mWidth = width;
            p.mHeight = height;
            return this;
        }

        public CommonWindow build() {
            CommonWindow commonWindow = new CommonWindow(context);
            p.apply(commonWindow);
            return commonWindow;
        }
    }

    public void showBelowCenter(View view) {
        int viewWidth = view.getMeasuredWidth();
        int contentViewWidth = getWidth();
        if (contentViewWidth <= 0) {
            contentViewWidth = getContentView().getMeasuredWidth();
        }
        int xPosition = (viewWidth - contentViewWidth) / 2;
        showAsDropDown(view, xPosition, 0);
    }

    public void showBelow(View view) {
        showAsDropDownCompile(view, 0, 0);
    }

    public void showBelow(View view , int xOff, int yOff) {
        showAsDropDownCompile(view, xOff, yOff);
    }

    public void showBelowCenter(View view, float alpha) {
        setPopupWindowBackground(alpha);
        showBelowCenter(view);
    }

    public void showTopCenter(View view) {
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        int contentViewWidth = getWidth();
        if (contentViewWidth <= 0) {
            contentViewWidth = getContentView().getMeasuredWidth();
        }
        int contentViewHeight = getHeight();
        if (contentViewHeight <= 0) {
            contentViewHeight = getContentView().getMeasuredHeight();
        }
        int xPosition = (viewWidth - contentViewWidth) / 2;
        int yPosition = -(contentViewHeight + viewHeight);
        showAsDropDown(view, xPosition, yPosition);
    }

    public void setPopupWindowBackground(float alpha) {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow()
                .getAttributes();
        lp.alpha = alpha;
        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)mContext).getWindow().setAttributes(lp);
    }

    private void showAsDropDownCompile(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
            showAsDropDown(anchor, xoff, yoff);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
}
