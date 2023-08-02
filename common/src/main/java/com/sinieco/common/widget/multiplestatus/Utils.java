package com.sinieco.common.widget.multiplestatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接Util
 * Created by LiuHe on 2019/8/23.
 */

class Utils {

    static boolean isNetworkConnected(Context context) {
        if (null != context) {
            ConnectivityManager connectivityManage = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManage.getActiveNetworkInfo();
            if (null != activeNetworkInfo) {
                return activeNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
