package com.sinieco.common.widget.multiplestatus.status_view;

import android.content.Context;

import com.sinieco.common.R;
import com.sinieco.common.widget.multiplestatus.BaseStatusView;

/**
 * 默认空数据状态布局
 */
public class EmptyView extends BaseStatusView {

    public EmptyView(Context context) {
        super(context, R.layout.status_view_empty);
    }

    @Override
    public void onAppear() {

    }

    @Override
    public void onDisappear() {

    }
}
