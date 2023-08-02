package com.sinieco.common.widget.recycler;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.sinieco.common.widget.recycler.listener.OnItemClickListener;
import com.sinieco.common.widget.recycler.listener.OnItemLongClickListener;

import java.util.List;

/**
 * RecyclerView Adapter
 * Created by LiuHe on 2018/4/25.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<T> mDataSource;
    private @LayoutRes
    int mLayoutResId;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    
    public CommonAdapter(Context context, @LayoutRes int layoutResId, List<T> dataSource) {
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mDataSource = dataSource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(mContext, null, parent, mLayoutResId, -1);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        setClickListener(holder.getParent(), holder, position);
        convert(holder, mDataSource.get(position));
    }

    private void setClickListener(final ViewGroup parent, final ViewHolder holder, final int position) {
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(parent, holder.itemView, position);
                }
            }
        });
        holder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(holder.getParent(), holder, position);
                }
                return false;
            }
        });
    }

    public abstract void convert(ViewHolder holder, T o);

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
