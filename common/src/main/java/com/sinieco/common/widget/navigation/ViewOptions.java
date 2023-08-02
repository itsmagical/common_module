package com.sinieco.common.widget.navigation;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Dimension;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static androidx.annotation.Dimension.PX;

/**
 *
 * Created by Administrator on 2019/5/27.
 */

public class ViewOptions implements Options<View> {

    public static Builder Builder() {
        return new Builder();
    }

    static final int DEFAULT_VISIBILITY = View.VISIBLE;
    static final int DEFAULT_WIDTH = ViewGroup.LayoutParams.WRAP_CONTENT;
    static final int DEFAULT_HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT;
    static final int DEFAULT_PADDING = 0;

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {

    }

    int visibility = DEFAULT_VISIBILITY;
    @Dimension(unit = PX)
    int paddingLeft = DEFAULT_PADDING;
    @Dimension(unit = PX)
    int paddingTop = DEFAULT_PADDING;
    @Dimension(unit = PX)
    int paddingRight = DEFAULT_PADDING;
    @Dimension(unit = PX)
    int paddingBottom = DEFAULT_PADDING;
    @Dimension(unit = PX)
    int widthWithoutPadding = DEFAULT_WIDTH;
    @Dimension(unit = PX)
    int heightWithoutPadding = DEFAULT_HEIGHT;
    View.OnClickListener listener;

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public void completion(View view) {
        view.setVisibility(visibility);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        // set view width and height of params
        int validWidth = Utils.isLayoutParamsSpecialValue(widthWithoutPadding)
                ? widthWithoutPadding : widthWithoutPadding + view.getPaddingLeft() + paddingRight;
        int validHeight = Utils.isLayoutParamsSpecialValue(heightWithoutPadding)
                ? heightWithoutPadding : heightWithoutPadding + view.getPaddingTop() + view.getPaddingBottom();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (null ==  params) {
            params = new ViewGroup.LayoutParams(validWidth, validHeight);
        } else {
            params.width = validWidth;
            params.height = validHeight;
        }
        view.setLayoutParams(params);
        // set onClickListener
        if (null != listener) {
            view.setOnClickListener(listener);
        }
    }

    private void copyFrom(ViewOptions other) {
        this.visibility = other.visibility;
        this.paddingLeft = other.paddingLeft;
        this.paddingTop = other.paddingTop;
        this.paddingRight = other.paddingRight;
        this.paddingBottom = other.paddingBottom;
        this.widthWithoutPadding = other.widthWithoutPadding;
        this.heightWithoutPadding = other.heightWithoutPadding;
        if (null != other.listener) {
            this.listener = other.listener;
        }
    }

    public static class Builder {
        ViewOptions op;

        private Builder() {
            op = new ViewOptions();
        }

        private Builder(ViewOptions other) {
            this();
            op.copyFrom(other);
        }

        public Builder setVisibility(int visibility) {
            op.visibility = visibility;
            return this;
        }

        public Builder setPaddingLeft(@Dimension(unit = PX) int paddingLeft) {
            op.paddingLeft = paddingLeft;
            return this;
        }

        public Builder setPaddingTop(@Dimension(unit = PX) int paddingTop) {
            op.paddingTop = paddingTop;
            return this;
        }

        public Builder setPaddingRight(@Dimension(unit = PX) int paddingRight) {
            op.paddingRight = paddingRight;
            return this;
        }

        public Builder setPaddingBottom(@Dimension(unit = PX) int paddingBottom) {
            op.paddingBottom = paddingBottom;
            return this;
        }

        public Builder setWidthExcludePadding(@Dimension(unit = PX) int widthExcludePadding) {
            op.widthWithoutPadding = widthExcludePadding;
            return this;
        }

        public Builder setHeightExcludePadding(@Dimension(unit = PX) int heightExcludePadding) {
            op.heightWithoutPadding = heightExcludePadding;
            return this;
        }

        public Builder setListener(View.OnClickListener listener) {
            op.listener = listener;
            return this;
        }

        public ViewOptions build() {
            return op;
        }

    }

}
