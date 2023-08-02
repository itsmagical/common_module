package com.sinieco.common.widget.fold_label;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * Created by LiuHe on 2018/9/4.
 */

public class EqualDivisionViewGroup extends ViewGroup {

    private Context context;

    protected int divisionCount;
    protected int marginHorizontal;
    protected int marginVertical;
    protected float labelTextSize = 15;
    protected int labelTextGravity = Gravity.CENTER;
    protected int showLineCount;

    private int labelWidth;
    public int labelHeight;

    public EqualDivisionViewGroup(Context context) {
        this(context, null);
    }

    public EqualDivisionViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EqualDivisionViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int usableWidth = width - (divisionCount - 1) * marginHorizontal;
        labelWidth = usableWidth / divisionCount;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (labelHeight != 0) {
                child.measure(MeasureSpec.makeMeasureSpec(labelWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(labelHeight, MeasureSpec.EXACTLY));
            } else {
                child.measure(MeasureSpec.makeMeasureSpec(labelWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.UNSPECIFIED));
            }
        }
        if (labelHeight == 0 && childCount > 0) {
            labelHeight = getChildAt(0).getMeasuredHeight();
        }
        if (showLineCount == 0) {
            int heightDimension = labelHeight;
            // Log.e("TAG", "onMeasure: " + heightDimension);
            heightDimension = heightDimension + getHeightDimension(childCount);
            setMeasuredDimension(widthMeasureSpec, heightDimension);
        }
    }

    /**
     *  根据label数量获取行数
     */
    private int getHeightDimension(int labelCount) {
        int heightDimension = 0;
        for (int i = 0; i < labelCount; i++) {
            if (i != 0 && i % divisionCount == 0) {
                if (i <= labelCount - 1) {
                    // Log.e("TAG", "getHeightDimension: " + i );
                    heightDimension = heightDimension + labelHeight + marginVertical;
                }
            }
        }
        return heightDimension;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = 0;
        int height = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i != 0 && i % divisionCount == 0) {
                if (i <= childCount - 1) {
                    height = height + labelHeight + marginVertical;
                    width = 0;
                }
            }
            View child = getChildAt(i);
            child.layout(width, height, width + labelWidth, height + labelHeight);
            if (i % divisionCount == divisionCount - 1) {
                width += labelWidth;
            } else {
                width = width + labelWidth + marginHorizontal;
            }
        }
    }

    private int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
    
}
