package com.sinieco.common.widget.multiplestatus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sinieco.common.widget.multiplestatus.status_view.EmptyView;
import com.sinieco.common.widget.multiplestatus.status_view.ErrorView;
import com.sinieco.common.widget.multiplestatus.status_view.LoadingView;
import com.sinieco.common.widget.multiplestatus.status_view.NetworkErrorView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 *
 * Created by LiuHe on 2019/8/17.
 */

public class StatusViewHelper implements StatusViewConfig  {

    private Context mContext;
    private long retryingInterval;

    private LinkedList<View> appearViews = new LinkedList<>();

    private View loadingView;
    private View emptyView;
    private View errorView;
    private View networkErrorView;

    private StatusHandler handler;

    private MultipleStatusLayout mMultipleStatusLayout;

    StatusViewHelper(MultipleStatusLayout multipleStatusLayout) {
        mContext = multipleStatusLayout.getContext();
        mMultipleStatusLayout = multipleStatusLayout;

        handler = new StatusHandler(this);

        retryingInterval = MultipleStatusConfig.getInstance().getRetryingInterval();

        final MultipleStatusConfig config = MultipleStatusConfig.getInstance();

        /// 如果没有全局配置状态布局，则使用默认配置布局
        if (config.getStatusViewProvider() == null) {
            config.setStatusViewProvider(new StatusViewProvider() {
                @Override
                public View getLoadingView(Context context, StatusHandler handler) {
                    return new LoadingView(context);
                }

                @Override
                public View getEmptyView(Context context, StatusHandler handler) {
                    return new EmptyView(context);
                }

                @Override
                public View getErrorView(Context context, StatusHandler handler) {
                    return new ErrorView(context, handler);
                }

                @Override
                public View getNetworkErrorView(Context context, StatusHandler handler) {
                    return new NetworkErrorView(context);
                }
            });
        }

        StatusViewProvider provider = config.getStatusViewProvider();

        loadingView = provider.getLoadingView(mContext, handler);

        if (loadingView == null) {
            loadingView = new LoadingView(mContext);
        }

        emptyView = provider.getEmptyView(mContext, handler);
        if (emptyView == null) {
            emptyView = new EmptyView(mContext);
        }

        errorView = provider.getErrorView(mContext, handler);
        if (errorView == null) {
            errorView = new ErrorView(mContext, handler);
        }

        networkErrorView = provider.getNetworkErrorView(mContext, handler);
        if (networkErrorView == null) {
            networkErrorView = new NetworkErrorView(mContext);
        }

    }

    @Override
    public void setRetryingInterval(int retryingInterval) {
        this.retryingInterval = retryingInterval;
    }

    @Override
    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }

    @Override
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    @Override
    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }

    @Override
    public void setNetworkErrorView(View networkErrorView) {
        this.networkErrorView = networkErrorView;
    }

    @Override
    public View getLoadingView() {
        return loadingView;
    }

    @Override
    public View getEmptyView() {
        return emptyView;
    }

    @Override
    public View getErrorView() {
        return errorView;
    }

    @Override
    public View getNetworkError() {
        return networkErrorView;
    }

    public void showStatus(StatusOptions options) {
        if (options == StatusOptions.FINISHED) {
            removeAllStatusView();
        } else {
            setStatusView(options);
        }
    }

    private void removeAllStatusView() {
        for (View statusView : appearViews) {
            onDisappear(statusView);
            removeStatusView(statusView);
        }
        appearViews.clear();
    }

    private void setStatusView(StatusOptions options) {
        View statusView = null;

        switch (options) {
            case LOADING: {
                statusView = loadingView;
                break;
            }
            case EMPTY: {
                statusView = emptyView;
                break;
            }
            case ERROR: {
                statusView = errorView;
                break;
            }
            case NETWORK_ERROR: {
                statusView = networkErrorView;
                break;
            }
        }

        if (removeTopView(statusView)) {
            addStatusView(statusView);
        }
        // 网络未连接
        if (!Utils.isNetworkConnected(mContext)) {
            statusView = networkErrorView;
            if (removeTopView(statusView)) {
                addStatusView(statusView);
            }

        }
    }

    private boolean removeTopView(View statusView) {
        if (appearViews.size() > 0) {
            View topView = appearViews.getFirst();
            if (statusView == topView) {
                return false;
            }

            if (appearViews.contains(topView)) {
                appearViews.remove(topView);
                onDisappear(topView);
                removeStatusView(topView);
            }
        }
        return true;
    }

    /**
     *  ViewRender 被移除之前 调用其onDisappear生命周期方法
     * @param statusView 将被移除的状态布局;
     */
    private void onDisappear(View statusView) {
        if (statusView instanceof BaseStatusView) {
            ((BaseStatusView) statusView).onDisappear();
        }
    }

    private void removeStatusView(View statusView) {
        if (null != statusView) {
            mMultipleStatusLayout.removeView(statusView);
        }
    }

    /**
     *  添加StatusView
     */
    private void addStatusView(View statusView) {
        checkViewIsNull(statusView);

        if (!appearViews.contains(statusView)) {
            appearViews.add(statusView);
            onAppear(statusView);
            mMultipleStatusLayout.addView(statusView, createLayoutParams());
        }
    }

    /**
     *  status被添加之前 调用onAppear();
     * @param statusView 将要被添加statusView
     */
    private void onAppear(View statusView) {
        if (statusView instanceof BaseStatusView) {
            ((BaseStatusView) statusView).onAppear();
        }
    }

    private ViewGroup.LayoutParams createLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }


    /**
     *  检查StatusView是否为空
     */
    private void checkViewIsNull(View statusView) {
        if (null == statusView) {
            throw new NullPointerException("please initialize MultipleStatusConfig statusView in Application" +
                    " or set statusView in MultipleStatusLayout");
        }
    }

    public void onRetrying() {
        if (retryingInterval > 0 && (!isRetrying())) {return;}
        OnRetryingListener retryingListener = mMultipleStatusLayout.getRetryingListener();
        if (null != retryingListener) {
            retryingListener.onRetrying();
        }
    }

    private long momentTime;


    public boolean isRetrying() {
        if (System.currentTimeMillis() - momentTime < retryingInterval) {
            return false;
        }
        momentTime = System.currentTimeMillis();
        return true;
    }

}
