package com.sinieco.common.widget.common_dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 * Created by LiuHe on 2018/8/7.
 */
class DialogController {
    private CommonDialog alertDialog;
    private Window window;
    private DialogViewHelper dialogViewHelper;
    public DialogController(CommonDialog alertDialog, Window window) {
        this.alertDialog = alertDialog;
        this.window = window;
    }

    public CommonDialog getAlertDialog() {
        return alertDialog;
    }

    public Window getWindow() {
        return window;
    }

    public <T extends View> T getView(int idRes) {
        if (dialogViewHelper != null) {
            return dialogViewHelper.getView(idRes);
        }
        return null;
    }

    public void setText(int idRes, CharSequence text) {
        dialogViewHelper.setText(idRes, text);
    }

    public void setOnClick(int idRes, View.OnClickListener listener) {
        dialogViewHelper.setOnClick(idRes, listener);
    }

    public void setVisibility(int idRes, int visibility) {
        dialogViewHelper.setVisibility(idRes, visibility);
    }

    static class AlertParams {
        public Context context;
        public int themeResId;
        public View view;
        public int layoutResId;
        public boolean cancelable = true;
        public int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        public DialogInterface.OnCancelListener onCancelListener;
        public DialogInterface.OnDismissListener onDismissListener;
        public DialogInterface.OnKeyListener onKeyListener;
        public SparseArray<CharSequence> textArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> clickArray = new SparseArray<>();
        public int animation = 0;
        public int gravity = Gravity.CENTER;

        public AlertParams(Context context, int themeResId) {
            this.context = context;
            this.themeResId = themeResId;
        }

        public void apply(DialogController alertController) {
            DialogViewHelper dialogViewHelper = null;
            if (layoutResId != 0) {
                dialogViewHelper = new DialogViewHelper(context, layoutResId);
            }

            if (view != null) {
                dialogViewHelper = new DialogViewHelper();
                dialogViewHelper.setContentView(view);
            }
            alertController.dialogViewHelper = dialogViewHelper;
            if (dialogViewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }

            alertController.getAlertDialog().setContentView(dialogViewHelper.getContentView());

            // 设置文本
            int textArraySize = textArray.size();
            for (int i = 0; i < textArraySize; i++) {
                dialogViewHelper.setText(textArray.keyAt(i), textArray.valueAt(i));
            }

            // 设置点击事件
            int clickArraySize = clickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                dialogViewHelper.setOnClick(clickArray.keyAt(i), clickArray.valueAt(i));
            }

            Window window = alertController.getWindow();
            window.setGravity(gravity);
            if (animation != 0) {
                window.setWindowAnimations(animation);
            }
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = width;
            params.height = height;
            window.setAttributes(params);


        }
    }
}
