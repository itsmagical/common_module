package com.sinieco.common.widget.multiplestatus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 多状态加载布局
 * 可全局或局部配置状态布局
 * 局部优先级大于全局
 * 包含 加载中、空数据、加载错误、网络错误
 * Created by  on 2019/8/17.
 */

public class MultipleStatusLayout extends FrameLayout implements StatusViewConfig {

    private StatusViewHelper mStatusViewHelper;
    private OnRetryingListener mRetryingListener;

    public MultipleStatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStatusViewHelper = new StatusViewHelper(this);
    }

    /**
     * 根据状态类型 显示状态布局
     * @param options 状态类型
     */
    public void showStatus(StatusOptions options) {
        if (null != options) {
            mStatusViewHelper.showStatus(options);
        }
    }

    @Override
    public void setRetryingInterval(int interval) {
        interval = interval > MultipleStatusConfig.MAX_RETRYING_INTERVAL ? MultipleStatusConfig.MAX_RETRYING_INTERVAL : interval;
        mStatusViewHelper.setRetryingInterval(interval);
    }

    @Override
    public void setLoadingView(View loadingView) {
        mStatusViewHelper.setLoadingView(loadingView);
    }

    @Override
    public void setEmptyView(View emptyView) {
        mStatusViewHelper.setLoadingView(emptyView);
    }

    @Override
    public void setErrorView(View errorView) {
        mStatusViewHelper.setLoadingView(errorView);
    }

    @Override
    public void setNetworkErrorView(View networkErrorView) {
        mStatusViewHelper.setLoadingView(networkErrorView);
    }

    @Override
    public View getLoadingView() {
        return mStatusViewHelper.getLoadingView();
    }

    @Override
    public View getEmptyView() {
        return mStatusViewHelper.getEmptyView();
    }

    @Override
    public View getErrorView() {
        return mStatusViewHelper.getErrorView();
    }

    @Override
    public View getNetworkError() {
        return mStatusViewHelper.getNetworkError();
    }

    public void setOnRetryingListener(OnRetryingListener listener) {
        mRetryingListener = listener;
    }

    public OnRetryingListener getRetryingListener() {
        return mRetryingListener;
    }

}
