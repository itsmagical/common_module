package com.sinieco.common.widget.multiplestatus;

import android.content.Context;
import android.view.View;

public interface StatusViewProvider {

    View getLoadingView(Context context, StatusHandler handler);

    View getEmptyView(Context context, StatusHandler handler);

    View getErrorView(Context context, StatusHandler handler);

    View getNetworkErrorView(Context context, StatusHandler handler);

}
