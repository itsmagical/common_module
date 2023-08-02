package com.sinieco.common.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.sinieco.arch.util.Utils;

/**
 * 版本Utils
 * Created by LiuHe on 2018/9/23.
 */

public class VersionUtils {

    public static int getVersionCode() {
        PackageManager packageManager = Utils.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(Utils.getContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName() {
        String versionName = "1.0";
        PackageManager packageManager = Utils.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(Utils.getContext().getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}
