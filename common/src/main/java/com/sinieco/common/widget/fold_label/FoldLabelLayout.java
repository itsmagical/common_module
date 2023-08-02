package com.sinieco.common.widget.fold_label;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.sinieco.common.R;

import java.util.List;

/**
 * 可折叠的标签选择器
 * Created by LiuHe on 2018/9/4.
 */

public class FoldLabelLayout extends EqualDivisionViewGroup {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private int labelBackgroundRds;
    private int labelTextColorRes;
    private View preSelectedLabel;
    private float percent;
    private TranslationAnimator translationAnimator;
    private int widthMeasureSpec;
    private boolean isFold;
    private Integer defaultSelectedLablePosition;
    private OnLabelSelectedListener listener;

    public FoldLabelLayout(Context context) {
        this(context, null);
    }

    public FoldLabelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldLabelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttributes(attrs);
        translationAnimator = new TranslationAnimator();
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FoldLabelLayout);
        labelTextSize = attributes.getInt(R.styleable.FoldLabelLayout_label_text_size, 15);
        labelBackgroundRds = attributes.getResourceId(R.styleable.FoldLabelLayout_label_background_state, 0);
        labelTextColorRes = attributes.getResourceId(R.styleable.FoldLabelLayout_label_text_color_state, 0);
        divisionCount = attributes.getInt(R.styleable.FoldLabelLayout_division_count, 3);
        showLineCount = attributes.getInt(R.styleable.FoldLabelLayout_show_line_count, 0);
        int labelHeightRes = attributes.getResourceId(R.styleable.FoldLabelLayout_label_height, 0);
        int marginRes = attributes.getResourceId(R.styleable.FoldLabelLayout_margin, 0);
        int marginHorizontalRes = attributes.getResourceId(R.styleable.FoldLabelLayout_margin_horizontal, 0);
        int marginVerticalRes = attributes.getResourceId(R.styleable.FoldLabelLayout_margin_vertical, 0);
        if (labelHeightRes != 0) {
            labelHeight = getDimension(labelHeightRes);
        }
        if (marginRes != 0) {
            int margin = getDimension(marginRes);
            marginHorizontal = margin;
            marginVertical = margin;
        }
        if (marginHorizontalRes != 0) {
            marginHorizontal = getDimension(marginHorizontalRes);
        }
        if (marginVerticalRes != 0) {
            marginVertical = getDimension(marginVerticalRes);
        }
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.widthMeasureSpec = widthMeasureSpec;
        if (showLineCount != 0) {
            int childCount = getChildCount();
            int heightDimension = labelHeight;
            int showLabelCount = showLineCount * divisionCount;
            heightDimension = heightDimension + getHeightDimension(showLabelCount);
            int offsetDimension = getHeightDimension(childCount) - heightDimension + labelHeight;
            int height = (int) (heightDimension + percent * offsetDimension);
            setMeasuredDimension(widthMeasureSpec, height);
        }
    }

    private int getHeightDimension(int labelCount) {
        int heightDimension = 0;
        for (int i = 0; i < labelCount; i++) {
            if (i != 0 && i % divisionCount == 0) {
                if (i <= labelCount - 1) {
                    heightDimension = heightDimension + labelHeight + marginVertical;
                }
            }
        }
        return heightDimension;
    }

    public void setOptions(List<LabelOptionBean> beans) {
        removeAllViews();
        int n = 0;
        for (int i = 0; i < beans.size(); i++) {
            LabelOptionBean bean = beans.get(i);
            TextView tv = new TextView(context);
            tv.setText(bean.getLabelName());
            tv.setTag(++n);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelTextSize);
            tv.setGravity(labelTextGravity);
            // tv.setBackgroundColor(Color.GREEN);
            if (labelBackgroundRds != 0) {
                tv.setBackgroundResource(labelBackgroundRds);
            }
            if (labelTextColorRes != 0) {
                ColorStateList textColor = getResources().getColorStateList(labelTextColorRes);
                tv.setTextColor(textColor);
            }
            if (defaultSelectedLablePosition != null && defaultSelectedLablePosition == i) {
                tv.setSelected(true);
                preSelectedLabel = tv;
            }
            addView(tv);
        }
        requestLayout();
        setClick();
    }

    public <T> void setTags(List<T> tags) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            T tag = tags.get(i);
            child.setTag(tag);
        }
    }

    public <T> void setSelectedByTag(T tag) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Object childTag = child.getTag();

        }
    }

    private void setClick() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (preSelectedLabel != null) {
                        preSelectedLabel.setSelected(false);
                    }
                    v.setSelected(true);
                    preSelectedLabel = v;
                    if (listener != null) {
                        listener.onLabelSelected(v, 0);
                    }
                }
            });
        }
    }

    public void setLabelSelected(int position) {
        this.defaultSelectedLablePosition = position;
        int count = getChildCount();
        if (position < count) {
            View child = getChildAt(position);
            if (preSelectedLabel != null) {
                preSelectedLabel.setSelected(false);
            }
            child.setSelected(true);
            preSelectedLabel = child;
            if (listener != null) {
                listener.onLabelSelected(child, 0);
            }
        }
    }

    public void setToggle(int time) {
        if (!isFold) {
            setTranslateAnimator(0f, 1f, time);
        } else {
            setTranslateAnimator(1f, 0f, time);
        }
        isFold = !isFold;
    }

    public void setShow() {

    }

    public void setFold() {

    }

    public void setTranslateAnimator(float start, float end, int time) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.setDuration(time);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                requestLayout();
            }
        });
        valueAnimator.start();
    }

    private int getDimension(int dimensionRes) {
        return (int) context.getResources().getDimension(dimensionRes);
    }

    class TranslationAnimator extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            percent = interpolatedTime;
            if (percent == 0) {
                isFold = true;
            } else if (percent == 1) {
                isFold = false;
            }
            requestLayout();
        }
    }

    public interface OnLabelSelectedListener {
        void onLabelSelected(View view, int state);
    }

    public void setOnLabelSelectedListener(OnLabelSelectedListener listener) {
        this.listener = listener;
    }

}
