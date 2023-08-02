package com.sinieco.common.widget.common_dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 *
 * Created by LiuHe on 2018/8/7.
 */
public class DialogViewHelper {

    private View contentView;
    private SparseArray<View> mViwes = new SparseArray<>();

    public DialogViewHelper(Context context, @LayoutRes int layoutResId) {
        contentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }

    public DialogViewHelper() {

    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    void setText(int idRes, CharSequence text) {
        TextView textView = getView(idRes);
        textView.setText(text);
    }


    void setOnClick(int idRes, View.OnClickListener onClickListener) {
        View view = getView(idRes);
        view.setOnClickListener(onClickListener);
    }

    void setVisibility(int idRes, int visibility) {
        View view = getView(idRes);
        view.setVisibility(visibility);
    }

    public View getContentView() {
        return contentView;
    }

    public  <T extends View> T getView(@IdRes int idRes) {
        View view = mViwes.get(idRes);
        if (view == null) {
            view = contentView.findViewById(idRes);
            if (view == null) {
                throw new NullPointerException("id错误");
            }
            mViwes.put(idRes, view);
        }
        return (T) view;
    }



}
