package com.sinieco.common.widget.common_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.sinieco.common.R;

/**
 *
 * Created by LiuHe on 2018/8/7.
 */
public class CommonDialog extends Dialog {

    private final DialogController controller;

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        controller = new DialogController(this, getWindow());
    }

    public <T extends View> T getView(@IdRes int idRes) {
        return controller.getView(idRes);
    }

    /**
     *  设置text
     * @param idRes view id
     * @param text 文本
     */
    public void setText(@IdRes int idRes, CharSequence text) {
        controller.setText(idRes, text);
    }

    public void setVisibility(@IdRes int idRes, int visibility) {
        controller.setVisibility(idRes, visibility);
    }

    /**
     *  设置点击事件
     * @param idRes view id
     * @param listener 监听
     */
    public void setOnClickListener(@IdRes int idRes, View.OnClickListener listener) {
        controller.setOnClick(idRes, listener);
    }

    public static class Builder {
        private final DialogController.AlertParams p;

        public Builder(Context context) {
            p = new DialogController.AlertParams(context, R.style.DialogDefaultTheme);
        }

        public Builder(Context context, int themeResId) {
            p = new DialogController.AlertParams(context, themeResId);
        }

        public Builder setContentView(View view) {
            p.view = view;
            p.layoutResId = 0;
            return this;
        }

        public Builder setContentView(@LayoutRes int layoutResId) {
            p.view = null;
            p.layoutResId = layoutResId;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            p.cancelable = cancelable;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            p.onKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            p.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            p.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setText(@IdRes int viewId, CharSequence text) {
            p.textArray.put(viewId, text);
            return this;
        }

        public Builder setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
            p.clickArray.put(viewId, onClickListener);
            return this;
        }

        public Builder fillWidth() {
            p.width = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                p.animation = R.style.dialog_from_bottom_anim;
            }
            p.gravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setWidth(int width) {
            p.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            p.height = height;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            p.width = width;
            p.height = height;
            return this;
        }

        public Builder addDefaultAnimation() {
            //p.animation = R.style.dialog_
            return this;
        }

        public Builder addAnimation(@AnimRes int animRes) {
            p.animation = animRes;
            return this;
        }

        public CommonDialog create() {
            CommonDialog alertDialog = new CommonDialog(p.context, p.themeResId);
            p.apply(alertDialog.controller);
            alertDialog.setCancelable(p.cancelable);
            if (p.cancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(p.onCancelListener);
            alertDialog.setOnDismissListener(p.onDismissListener);
            if (p.onKeyListener != null) {
                alertDialog.setOnKeyListener(p.onKeyListener);
            }
            return alertDialog;
        }

        public CommonDialog show() {
            final CommonDialog alertDialog = create();
            alertDialog.show();
            return alertDialog;
        }
    }

    public void show(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            super.show();
        }
    }
}
