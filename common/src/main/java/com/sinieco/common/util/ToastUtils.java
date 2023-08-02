package com.sinieco.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.Toast;

import com.sinieco.arch.util.Utils;

/**
 * Toast
 * Created by LiuHe on 2018/9/19.
 */

public class ToastUtils {

    private static Toast toast;

    /**
     * 短时间显示toast
     */
    public static void show(String message) {
        ensureToast(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    /**
     * 长时间显示toast
     */
    public static void showLong(String message) {
        ensureToast(Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();
    }

    /**
     * 短时间居中显示toast
     */
    public static void showCenter(String message) {
        ensureToast(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(message);
        toast.show();
    }

    /**
     * 长时间居中显示toast
     */
    public static void showLongCenter(String message) {
        ensureToast(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(message);
        toast.show();
    }

    /**
     * 短时间居上显示toast
     */
    public static void showTop(String message) {
        ensureToast(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, getDefaultYOffset());
        toast.setText(message);
        toast.show();
    }

    /**
     * 长时间居上显示toast
     */
    public static void showLongTop(String message) {
        ensureToast(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, getDefaultYOffset());
        toast.setText(message);
        toast.show();
    }

    private static void ensureToast(int duration) {
        if (toast == null) {
            toast = Toast.makeText(Utils.getContext(), null, Toast.LENGTH_SHORT);
        }

        if (duration != toast.getDuration()) {
            toast.setDuration(duration);
        }
    }

    /**
     * y轴默认偏移距离
     * 参考构造{@link Toast#Toast(Context)}
     */
    private static int getDefaultYOffset() {
        Resources resources = Resources.getSystem();
        int id = resources.getIdentifier(
                "toast_y_offset", "dimen", "android");
        return resources.getDimensionPixelSize(id);
    }
}
