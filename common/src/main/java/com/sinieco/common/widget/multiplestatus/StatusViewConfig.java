package com.sinieco.common.widget.multiplestatus;

import android.view.View;

/**
 *
 * Created by LiuHe on 2019/8/17.
 */

public interface StatusViewConfig {

    /**
     *  重试间隔
     * @param interval 0 - 2000
     */
    void setRetryingInterval(int interval);

    /**
     *  设置加载中View
     */
    void setLoadingView(View loadingView);

    /**
     *  设置加载为空View
     */
    void setEmptyView(View emptyView);

    /**
     *  设置加载错误View
     */
    void setErrorView(View errorView);

    /**
     *  设置网络错误View
     */
    void setNetworkErrorView(View networkErrorView);

    View getLoadingView();

    View getEmptyView();

    View getErrorView();

    View getNetworkError();
}
