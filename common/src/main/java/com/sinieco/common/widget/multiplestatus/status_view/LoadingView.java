package com.sinieco.common.widget.multiplestatus.status_view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.sinieco.arch.widget.common_dialog.CommonDialog;
import com.sinieco.common.R;
import com.sinieco.common.widget.multiplestatus.BaseStatusView;

/**
 * 默认加载中 loading弹窗
 */
public class LoadingView extends BaseStatusView {

    private CommonDialog loadingDialog;

    public LoadingView(Context context) {
        super(context, R.layout.status_view_loading);
        createLoadingDialog(context);
    }

    @Override
    public void onAppear() {
        loadingDialog.show();
    }

    @Override
    public void onDisappear() {
        loadingDialog.dismiss();
    }

    private void createLoadingDialog(final Context context) {
        loadingDialog = new CommonDialog.Builder(context)
                .setContentView(R.layout.view_alert_loading)
                .create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }

                return false;
            }
        });
    }
}
