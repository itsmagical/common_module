package com.sinieco.common.widget.tabbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;


/**
 * Created by Administrator on 2019/5/30.
 */

class TintUtil  {

    static Drawable setStateListTintColorId(Context context, Drawable drawable, int colorId, int selectedColorId) {
        return setStateListTintColor(drawable, ContextCompat.getColor(context, colorId), ContextCompat.getColor(context, selectedColorId));
    }

    static Drawable setStateListTintColor(Drawable drawable, int color, int selectedColor) {
        int[] colors = new int[]{selectedColor, selectedColor, color};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        StateListDrawable stateListDrawable = getStateListDrawable(drawable, states);
        ColorStateList colorStateList = new ColorStateList(states, colors);
        return getStateDrawable(stateListDrawable, colorStateList);
    }

    static Drawable getStateDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        Drawable wrapDrawable = DrawableCompat.wrap(constantState == null ? drawable : constantState.newDrawable()).mutate();
        wrapDrawable.setBounds(0, 0, wrapDrawable.getIntrinsicWidth(), wrapDrawable.getIntrinsicHeight());
        DrawableCompat.setTintList(wrapDrawable, colorStateList);
        return wrapDrawable;
    }

    static StateListDrawable getStateListDrawable(Drawable drawable, int[][] states) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int[] state : states) {
            stateListDrawable.addState(state, drawable);
        }
        return stateListDrawable;
    }

}
