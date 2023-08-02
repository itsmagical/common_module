package com.sinieco.common.widget.navigation;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;

import static androidx.annotation.Dimension.SP;
import static androidx.annotation.Dimension.PX;


/**
 *
 * Created by admin on 2019/5/26.
 */
public class TextViewOptions implements Options<TextView> {

    public static Builder Builder() {
        return new Builder();
    }

    static final int UN_INITIALIZE_TEXT_SIZE = 0;
    static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    static final int DEFAULT_TITLE_TEXT_SIZE = 18;
    static final int DEFAULT_MENU_TEXT_SIZE = 13;
    static final int DEFAULT_MAX_EMS = 8;
    static final int DEFAULT_LINES = 1;
    static final int DEFAULT_PADDING = 0;
    static final TextUtils.TruncateAt DEFAULT_ELLIPSIZE = TextUtils.TruncateAt.END;

    CharSequence text;
    @Dimension(unit = Dimension.SP)
    int textSize = UN_INITIALIZE_TEXT_SIZE;
    @ColorInt
    int textColor = DEFAULT_TEXT_COLOR;
    int textStyle = Typeface.NORMAL;
    int maxEms = DEFAULT_MAX_EMS;
    int lines = DEFAULT_LINES;
    TextUtils.TruncateAt ellipsize = DEFAULT_ELLIPSIZE;
    @Dimension(unit = Dimension.PX)
    int leftPadding = DEFAULT_PADDING;
    @Dimension(unit = Dimension.PX)
    int rightPadding = DEFAULT_PADDING;
    View.OnClickListener listener = null;

    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public void completion(TextView textView) {
        textView.setPadding(leftPadding, 0, rightPadding, 0);
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setTypeface(Typeface.defaultFromStyle(textStyle));
        textView.setMaxEms(maxEms);
        textView.setLines(lines);
        textView.setEllipsize(ellipsize);
        if (null != listener) {
            textView.setOnClickListener(listener);
        }
    }

    private void copyFrom(@NonNull TextViewOptions other) {
        this.text = other.text;
        this.textSize = other.textSize;
        this.textColor = other.textColor;
        this.textStyle = other.textStyle;
        this.maxEms = other.maxEms;
        this.lines = other.lines;
        this.ellipsize = other.ellipsize;
        this.leftPadding = other.leftPadding;
        this.rightPadding = other.rightPadding;
        this.listener = other.listener;
    }

    public static class Builder {

        private TextViewOptions op;

        private Builder() {
            op = new TextViewOptions();
        }

        public Builder(TextViewOptions other) {
            this();
            op.copyFrom(other);
        }

        public Builder setText(CharSequence text) {
            op.text = text;
            return this;
        }

        public Builder setTextColor(@ColorInt int color) {
            op.textColor = color;
            return this;
        }

        public Builder setTextSize(@Dimension(unit = SP) int size) {
            op.textSize = size;
            return this;
        }

        public Builder setTextStyle(int textStyle) {
            op.textStyle = textStyle;
            return this;
        }

        public Builder setMaxEms(int maxEms) {
            op.maxEms = maxEms;
            return this;
        }

        public Builder setLines(int lines) {
            op.lines = lines;
            return this;
        }

        public Builder setEllipsize(TextUtils.TruncateAt ellipsize) {
            op.ellipsize = ellipsize;
            return this;
        }

        public Builder setLeftPadding(@Dimension(unit = PX) int leftPadding) {
            op.leftPadding = leftPadding;
            return this;
        }

        public Builder setRightPadding(@Dimension(unit = PX) int rightPadding) {
            op.rightPadding = rightPadding;
            return this;
        }

        public Builder setListener(View.OnClickListener listener) {
            op.listener = listener;
            return this;
        }

        public TextViewOptions build() {
            if (null == op) {
                throw new UnsupportedOperationException("please ensure text field nonnull ");
            }
            return op;
        }

    }

}
