package com.sinieco.common.widget.recycler.listener;

import android.view.ViewGroup;

import com.sinieco.common.widget.recycler.ViewHolder;


/**
 *  CommonAdapter Item的长按接口
 * Created by LiuHe on 2018/4/25.
 */

public interface OnItemLongClickListener {
    void onItemLongClick(ViewGroup parent, ViewHolder holder, int position);
}
