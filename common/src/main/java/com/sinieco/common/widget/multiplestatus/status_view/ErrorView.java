package com.sinieco.common.widget.multiplestatus.status_view;

import android.content.Context;
import android.view.View;

import com.sinieco.common.R;
import com.sinieco.common.widget.multiplestatus.BaseStatusView;
import com.sinieco.common.widget.multiplestatus.StatusHandler;

/**
 * 默认错误状态布局
 */
public class ErrorView extends BaseStatusView {

    public ErrorView(Context context, final StatusHandler handler) {
        super(context, R.layout.status_view_error, handler);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.onRetryingCommand();
            }
        });

    }

    @Override
    public void onAppear() {

    }

    @Override
    public void onDisappear() {

    }
}
