package com.sinieco.common.widget.multiplestatus.status_view;

import android.content.Context;

import com.sinieco.common.R;
import com.sinieco.common.widget.multiplestatus.BaseStatusView;

/**
 * 默认网络错误状态布局
 */
public class NetworkErrorView extends BaseStatusView {

    public NetworkErrorView(Context context) {
        super(context, R.layout.status_view_network_error);
    }

    @Override
    public void onAppear() {

    }

    @Override
    public void onDisappear() {

    }
}
