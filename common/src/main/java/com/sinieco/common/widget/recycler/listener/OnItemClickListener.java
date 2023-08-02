package com.sinieco.common.widget.recycler.listener;

import android.view.View;
import android.view.ViewGroup;

/**
 * CommonAdapter Item的点击接口
 * Created by LiuHe on 2018/4/25.
 */

public interface OnItemClickListener {

    void onItemClick(ViewGroup parent, View view, int position);
}
