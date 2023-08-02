package com.sinieco.common.widget.navigation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;

import static androidx.annotation.Dimension.PX;

/**
 * Created by admin on 2019/5/26.
 */
public class ImageViewOptions implements Options<ImageView> {


    public static Builder Builder() {
        return new Builder();
    }

    static final int UN_INITIALIZE_RES_ID = -1;
    static final ImageView.ScaleType DEFAULT_SACLE_TYPE = ImageView.ScaleType.CENTER_CROP;
    static final int DEFAULT_WIDTH = ViewGroup.LayoutParams.WRAP_CONTENT;
    static final int DEFAULT_HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT;
    static final int DEFAULT_PADDING = 0;

    @DrawableRes
    int drawableResId = UN_INITIALIZE_RES_ID;
    ImageView.ScaleType scaleType = DEFAULT_SACLE_TYPE;
    @Dimension(unit = PX)
    int paddingLeft = DEFAULT_PADDING;
    @Dimension(unit = PX)
    int paddingRight = DEFAULT_PADDING;
    @Dimension(unit = PX)
    int widthExcludePadding = DEFAULT_WIDTH;
    @Dimension(unit = PX)
    int heightExcludePadding = DEFAULT_HEIGHT;
    View.OnClickListener listener = null;

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public void completion(ImageView imageView) {
        imageView.setPadding(paddingLeft, 0, paddingRight, 0);
        int validWidth = Utils.isLayoutParamsSpecialValue(widthExcludePadding) ? widthExcludePadding :
                widthExcludePadding + imageView.getPaddingLeft() + imageView.getPaddingRight();
        int validHeight = Utils.isLayoutParamsSpecialValue(heightExcludePadding) ? heightExcludePadding :
                heightExcludePadding +imageView.getPaddingTop() + imageView.getPaddingBottom();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if (null == params) {
            params = new ViewGroup.LayoutParams(validWidth, validHeight);
        } else {
            params.width = validWidth;
            params.height = validHeight;
        }
        imageView.setLayoutParams(params);
        if (null != listener) {
            imageView.setOnClickListener(listener);
        }
        if (drawableResId > 0) {
            imageView.setImageResource(drawableResId);
        }
        imageView.setScaleType(scaleType);
    }

    private void copyFrom(ImageViewOptions other) {
        this.drawableResId = other.drawableResId;
        this.scaleType = other.scaleType;
        this.paddingLeft = other.paddingLeft;
        this.paddingRight = other.paddingRight;
        this.widthExcludePadding = other.widthExcludePadding;
        this.heightExcludePadding = other.heightExcludePadding;
        if (null != other.listener) {
            this.listener = other.listener;
        }
    }

    public static class Builder {

        ImageViewOptions op;

        private Builder() {
            op = new ImageViewOptions();
        }

        private Builder(ImageViewOptions other) {
            this();
            op.copyFrom(other);
        }

        public Builder setDrawableResId(@DrawableRes int drawableResId) {
            op.drawableResId = drawableResId;
            return this;
        }

        public Builder setScaleType(ImageView.ScaleType scaleType) {
            op.scaleType = scaleType;
            return this;
        }

        public Builder setPaddingLeft(@Dimension(unit = PX) int leftPadding) {
            op.paddingLeft = leftPadding;
            return this;
        }

        public Builder setPaddingRight(@Dimension(unit = PX) int rightPadding) {
            op.paddingRight = rightPadding;
            return this;
        }

        public Builder setWidthWithoutPadding(@Dimension(unit = PX) int widthWithoutPadding) {
            op.widthExcludePadding = widthWithoutPadding;
            return this;
        }

        public Builder setHeightWithoutPadding(@Dimension(unit = PX) int heightWithoutPadding) {
            op.heightExcludePadding = heightWithoutPadding;
            return this;
        }

        public Builder setListener(View.OnClickListener listener) {
            op.listener = listener;
            return this;
        }

        public ImageViewOptions build() {
            return op;
        }

    }
}
