package com.sinieco.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sinieco.arch.util.Utils;


/**
 * bitmap水印Util
 * Created by LiuHe on 2019/12/17.
 */

public class ImageStampUtils {

    public static Bitmap drawTextToTopCenter(Context context, Bitmap bitmap, String text, int size, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setTextSize(dp2px(size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int left  = (bitmap.getWidth() / 2) - (bounds.width() / 2);
        return drawTextToBitmap(bitmap, text, paint, left, dp2px(100));
    }

    private static Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int left, int top) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        paint.setDither(true);
        paint.setFilterBitmap(true);
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, left, top, paint);
        return bitmap;
    }

    private static int dp2px(float dpValue){
        float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

}
