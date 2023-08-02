package com.sinieco.common.util;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 *  动画
 * Created by LiuHe on 2018/10/11.
 * 修改排行柱状图为0时的显示位置异常问题
 */

public class AnimationUtils {

    public static void rotationVertical(View view, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", from, to);
        animator.setDuration(300);
        animator.start();
    }

    public static void translationXInvisible(View view) {
       // ObjectAnimator.ofFloat(view, "translationX", 0, );
    }

}
