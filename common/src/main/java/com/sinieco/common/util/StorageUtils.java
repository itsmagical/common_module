package com.sinieco.common.util;

import android.os.Environment;

import com.sinieco.arch.util.Utils;

public class StorageUtils {

    public static String getExternalFileDir() {
        if (isSDCardEnableByEnvironment()) {
            return Utils.getContext().getExternalFilesDir(null).getPath();
        } else {
            return Utils.getContext().getFilesDir().getPath();
        }
    }

    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
