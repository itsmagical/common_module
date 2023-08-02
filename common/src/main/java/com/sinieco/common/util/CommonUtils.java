package com.sinieco.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.sinieco.arch.util.ToastUtils;
import com.sinieco.arch.util.Utils;
import com.sinieco.common.network.NetworkConfig;

import java.io.File;
import java.util.Collection;
import java.util.Random;

/**
 *
 * Created by LiuHe on 2018/9/23.
 */

public class CommonUtils {


    public static String getImageBaseUrl(String relativeUrl) {
        String baseUrl = NetworkConfig.getInstance().baseUrl;
        int index = baseUrl.indexOf("/", 10);
        if (index > 0) {
            return baseUrl.substring(0, index) + relativeUrl;
        }
        return baseUrl + relativeUrl;
    }

    public static String getApkUrl(String relativeUrl) {
        if (TextUtils.isEmpty(relativeUrl)) return "";
        int first = relativeUrl.indexOf("/");
        if (first == 0) {
            relativeUrl = relativeUrl.substring(1);
        }
        String baseUrl = NetworkConfig.getInstance().baseUrl;
        int index = baseUrl.indexOf("/", 10);
        if (index > 0) {
            return baseUrl.substring(0, index + 1) + relativeUrl;
        }
        return baseUrl + relativeUrl;
    }

    public static String getAttachmentUrl(String relativeUrl) {
        String baseUrl = NetworkConfig.getInstance().baseUrl;
        int index = baseUrl.indexOf("/", 10);
        if (index > 0) {
            return baseUrl.substring(0, index) + relativeUrl;
        }
        return baseUrl + relativeUrl;
    }

    public static String getStorageDirectory() {
        String packageName = Utils.getContext().getPackageName();
        Utils.getContext().getExternalCacheDir();
        String storageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + packageName;
        File file = new File(storageDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
        return storageDirectory;
    }

    public static File getFileInStorageDirectory(String fileName) {
        String fileUrl = getStorageDirectory() + "/" + fileName;
        File file = new File(fileUrl);
        return file;
    }

    public static String getAttachmentDirectory() {
        String attachmentDirectory = getStorageDirectory() + "/attachments/";
        File file = new File(attachmentDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
        return attachmentDirectory;
    }

    public static String getFileProvider() {
        String packageName = Utils.getContext().getPackageName();
        return packageName + ".fileProvider";
    }

    /**
     *  文件转Uri
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, CommonUtils.getFileProvider(), file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static String stringJoin(String... values) {
        String accumulationValue = null;
        if (values.length > 0) {
            for (String value : values) {
                if (TextUtils.isEmpty(accumulationValue)) {
                    accumulationValue = value;
                } else {
                    accumulationValue += value;
                }
            }
        }
        return accumulationValue;
    }

    public static String cutBaseUrlLastSlash(String baseUrl) {
        int index = baseUrl.lastIndexOf("/");
        if (index == baseUrl.length() -1) {
            return baseUrl.substring(0, index);
        }
        return baseUrl;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth() {
        Resources resources = Utils.getContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static void showRequestFail() {
        ToastUtils.showSingleToast("请求失败");
    }

    public static int getColor(Integer[] colors) {
        if (colors != null && colors.length == 3) {
            if (colors[0] != null && colors[1] != null && colors[2] != null) {
                return Color.rgb(colors[0], colors[1], colors[2]);
            }
        }
        return Color.parseColor("#FD8001");
        // return ResUtils.getColor(R.color._8B8B8B);
    }

    public static int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(100) + 100, (random.nextInt(100) + 100), (random.nextInt(100) + 100));
    }

    public static Integer[] getRandomRGB() {
        Random random = new Random();
        return new Integer[]{random.nextInt(100) + 120, (random.nextInt(100) + 120), (random.nextInt(100) + 120)};
    }

    public static int dp2px(float dpValue){
        float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static boolean isNotEmpty(Collection collection) {
        return null != collection && collection.size() > 0;
    }

    public static String getVideoControlStorageDirectory() {
        String directory = getStorageDirectory() + "/ivms8700";
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
        return directory;
    }

    public static Uri getFileUri(Context context, File file) {
        Uri uri = null;
        if (Utils.isNotNull(file)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, CommonUtils.getFileProvider(), file);
            } else {
                uri = Uri.fromFile(file);
            }
        }
        return uri;
    }

}
