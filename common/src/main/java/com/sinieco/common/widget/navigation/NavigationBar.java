package com.sinieco.common.widget.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinieco.arch.util.statusbar.StatusBarCompat;
import com.sinieco.common.R;

import static androidx.annotation.Dimension.SP;
import static androidx.annotation.Dimension.PX;

/**
 *
 * Created by admin on 2019/5/25.
 */
public class NavigationBar extends Toolbar {

    public static Builder Builder(Activity activity) {
        return new Builder(activity);
    }

    public static Builder Builder(View contentView) {
        return new Builder(contentView);
    }

    private static final int LOCKED_CHILDREN_COUNT = 3;
    private static final int DEFAULT_SUB_ITEM_INTERVAL = 5;

    @Dimension(unit = SP)
    private int mTitleTextSize = TextViewOptions.DEFAULT_TITLE_TEXT_SIZE;
    @Dimension(unit = SP)
    private int mMenuTextSize = TextViewOptions.DEFAULT_MENU_TEXT_SIZE;
    @Dimension(unit = PX)
    private int mMinimumHeight;
    @Dimension(unit = PX)
    private int mSubItemInterval;
    @Dimension(unit = PX)
    private int mDividingLineHeight;
    @ColorInt
    private int mTitleTextColor = TextViewOptions.DEFAULT_TEXT_COLOR;
    @ColorInt
    private int mMenuTextColor = TextViewOptions.DEFAULT_TEXT_COLOR;

    private LinearLayout mLeftMenuContainer;
    private LinearLayout mCenterContainer;
    private LinearLayout mRightMenuContainer;
    private TextView mTitleText;


    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        initDefaultArgs(context, typedArray);
        initViews(context);
        // set title gravity
        switch (typedArray.getInt(R.styleable.NavigationBar_titleGravity, -1)) {
            case 0:
                setTitleGravity(Gravity.START | Gravity.TOP);
                break;
            case 1:
                setTitleGravity(Gravity.END | Gravity.TOP);
                break;
            case 2:
                setTitleGravity(Gravity.CENTER | Gravity.TOP);
                break;
            default:
                break;
        }
        // title
        String titleText = typedArray.getString(R.styleable.NavigationBar_titleText);
        setTitleText(titleText);
        // back icon
        int backIconResId = typedArray.getResourceId(R.styleable.NavigationBar_backIcon, View.NO_ID);
        if (backIconResId != View.NO_ID) {
            addBackIcon(backIconResId);
        }
        int leftMenuIconResId = typedArray.getResourceId(R.styleable.NavigationBar_menuLeftIcon, View.NO_ID);
        if (leftMenuIconResId != View.NO_ID) {
            addLeftMenuImage(ImageViewOptions.Builder().setDrawableResId(leftMenuIconResId).build());
        }
        String menuLeftText = typedArray.getString(R.styleable.NavigationBar_menuLeftText);
        if (null != menuLeftText) {
            addLeftMenuText(
                    TextViewOptions.Builder()
                            .setText(menuLeftText)
                            .setTextSize(mMenuTextSize)
                            .setTextColor(mMenuTextColor)
                            .build()
            );
        }

        String menuRightText = typedArray.getString(R.styleable.NavigationBar_menuRightText);
        if (null != menuLeftText) {
            addRightMenuText(
                    TextViewOptions
                            .Builder()
                            .setText(menuRightText)
                            .setTextSize(mMenuTextSize)
                            .setTextColor(mMenuTextColor)
                            .build()
            );
        }

        int rightMenuIconResId = typedArray.getResourceId(R.styleable.NavigationBar_menuRightIcon, View.NO_ID);
        if (rightMenuIconResId != View.NO_ID) {
            addRightMenuImage(
                    ImageViewOptions.Builder().setDrawableResId(rightMenuIconResId).build()
            );
        }
        typedArray.recycle();
    }

    public void setTitleGravity(int gravity) {
        LayoutParams params = (LayoutParams) mCenterContainer.getLayoutParams();
        params.gravity = gravity;
        mCenterContainer.setGravity(gravity);
    }

    public void setTitleText(@StringRes int stringRes) {
        setTitleText(getResources().getString(stringRes));
    }

    public void setTitleText(@NonNull CharSequence title) {
        setTitleText(title, mTitleTextSize);
    }

    public void setTitleText(@NonNull CharSequence title, @Dimension(unit = SP) int textSize) {
        setTitleText(title, mTitleTextSize, mTitleTextColor);
    }

    public void setTitleText(@NonNull CharSequence title, @Dimension(unit = SP) int texSize, @ColorInt int textColor) {
        setTitleText(
                TextViewOptions.Builder()
                        .setText(title)
                        .setTextSize(texSize)
                        .setTextColor(textColor)
                        .build()
        );
    }

    public void setTitleText(@NonNull TextViewOptions op) {
        op.newBuilder()
                .setTextSize(TextViewOptions.UN_INITIALIZE_TEXT_SIZE != op.textSize
                        ? op.textSize : mTitleTextSize)
                .setLeftPadding(TextViewOptions.DEFAULT_PADDING != op.leftPadding
                        ? op.leftPadding : mSubItemInterval)
                .setRightPadding(TextViewOptions.DEFAULT_PADDING != op.rightPadding
                        ? op.rightPadding : mSubItemInterval)
                .build()
                .completion(getTitleText());
    }

    private TextView getTitleText() {
        if (null == mTitleText) {
            mTitleText = createTextView();
            addTitleView(mTitleText);
        }
        return mTitleText;
    }

    public void addTitleView(View view) {
        addTitleView(view, null);
    }

    public void addTitleView(View view, Options ops) {
        if (null != ops) {
            ops.completion(view);
        }
        mCenterContainer.addView(view);
    }

    public void addLeftMenuText(TextViewOptions ops) {
        addLeftMenuView(
                createTextView(), ops.newBuilder()
                        .setLeftPadding(TextViewOptions.DEFAULT_PADDING != ops.leftPadding
                                ? ops.leftPadding : mSubItemInterval)
                        .setRightPadding(TextViewOptions.DEFAULT_PADDING != ops.rightPadding
                                ? ops.rightPadding : mSubItemInterval).build()

        );
    }

    public void addRightMenuText(TextViewOptions ops) {
        addRightView(
                createTextView(), ops.newBuilder()
                        .setLeftPadding(TextViewOptions.DEFAULT_PADDING != ops.leftPadding
                                ? ops.leftPadding : mSubItemInterval)
                        .setRightPadding(TextViewOptions.DEFAULT_PADDING != ops.rightPadding
                                ? ops.rightPadding : mSubItemInterval).build()
                );
    }

    public void addBackIcon(@DrawableRes int drawableResId) {
        addLeftMenuImage(
                ImageViewOptions.Builder()
                        .setDrawableResId(drawableResId)
                .setListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getContext() instanceof Activity) {
                            ((Activity) getContext()).onBackPressed();
                        }
                    }
                }).build()

        );
    }

    public void addLeftMenuImage(@Nullable ImageViewOptions ops) {
        addLeftMenuView(
                createImageView(), ops.newBuilder()
                        .setPaddingLeft(ImageViewOptions.DEFAULT_PADDING != ops.paddingLeft
                        ? ops.paddingLeft : mSubItemInterval)
                        .build()
        );
    }

    public void addRightMenuImage(@Nullable ImageViewOptions ops) {
        addRightView(
                createImageView(), ops.newBuilder()
                        .setPaddingRight(ImageViewOptions.DEFAULT_PADDING != ops.paddingRight
                                ? ops.paddingRight : mSubItemInterval)
                        .build()
        );
    }

    public void addLeftMenuView(@Nullable View view) {
        addLeftMenuView(view, null);
    }

    public void addLeftMenuView(@Nullable View view, Options ops) {
        if (null != ops) {
            ops.completion(view);
        }
        mLeftMenuContainer.addView(view);
    }

    public void addRightView(@Nullable View view) {
        addRightView(view, null);
    }

    public void addRightView(@NonNull View view, Options ops) {
        if (null != ops) {
            ops.completion(view);
        }
        mRightMenuContainer.addView(view);
    }

    public void setStatusBarStyle(Style style) {
        switch (style) {
            case FILL:{
                if (getContext() instanceof Activity) {
                    setPadding(0, Utils.getStatusBarHeight(getContext()), 0, 0);
                    StatusBarCompat.setOverlay((Activity) getContext(), false);
                }
                break;
            }
            case DEFAULT:{

                break;
            }
        }
    }

    public void setBackgroundColorRes(@ColorRes int colorResId) {
        setBackgroundColor(ContextCompat.getColor(getContext(), colorResId));
    }

    public void setBackgroundDrawableResId(@DrawableRes int drawableResId) {
        setBackgroundResource(drawableResId);
    }

    public <T extends View> T getLeftMenuView(int index) {
        return (T) mLeftMenuContainer.getChildAt(index);
    }

    public <T extends View> T getRightMenuView(int index) {
        return (T) mRightMenuContainer.getChildAt(index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (LOCKED_CHILDREN_COUNT == getChildCount()) {
            return;
        }
        super.addView(child, index, params);
    }



    public void setMinimumHeight(int minimumHeight) {
        mMinimumHeight = minimumHeight;
        mCenterContainer.setMinimumHeight(minimumHeight);
        mLeftMenuContainer.setMinimumHeight(minimumHeight);
        mRightMenuContainer.setMinimumHeight(mMinimumHeight);
    }

    /**
     * menu spacing
     */
    public void setSubItemInterval(int subItemInterval) {
        mSubItemInterval = subItemInterval;
    }

    private void initDefaultArgs(Context context, TypedArray typedArray) {
        mMinimumHeight = (int) typedArray.getDimension(R.styleable.NavigationBar_minimumHeight,
                Utils.dp2px(context, 46));
        mSubItemInterval = (int) typedArray.getDimension(R.styleable.NavigationBar_subItemInterval,
                Utils.dp2px(context, DEFAULT_SUB_ITEM_INTERVAL));
        mTitleTextColor = typedArray.getColor(R.styleable.NavigationBar_titleTextColor, mTitleTextColor);
        mTitleTextSize = (int) typedArray.getDimension(R.styleable.NavigationBar_titleTextSize,
                mTitleTextSize);
        mMenuTextColor = typedArray.getColor(R.styleable.NavigationBar_menuTextColor, mMenuTextColor);
        mMenuTextSize = (int) typedArray.getDimension(R.styleable.NavigationBar_menuTextSize, mMenuTextSize);
    }

    private void initViews(Context context) {
        removeAllViews();
        mLeftMenuContainer = new LinearLayout(context);
        LayoutParams leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.gravity = Gravity.START | Gravity.TOP;
        mLeftMenuContainer.setLayoutParams(leftParams);
        mLeftMenuContainer.setMinimumHeight(mMinimumHeight);
        mLeftMenuContainer.setGravity(Gravity.CENTER_VERTICAL);
        addView(mLeftMenuContainer);

        mRightMenuContainer = new LinearLayout(context);
        LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.gravity = Gravity.END | Gravity.TOP;
        mRightMenuContainer.setLayoutParams(rightParams);
        mRightMenuContainer.setMinimumHeight(mMinimumHeight);
        mRightMenuContainer.setGravity(Gravity.CENTER_VERTICAL);
        addView(mRightMenuContainer);

        mCenterContainer = new LinearLayout(context);
        LayoutParams centerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        centerParams.gravity = Gravity.CENTER | Gravity.TOP;
        mCenterContainer.setLayoutParams(centerParams);
        mCenterContainer.setMinimumHeight(mMinimumHeight);
        mCenterContainer.setGravity(Gravity.CENTER_VERTICAL);
        mCenterContainer.setPadding(mSubItemInterval, 0, mSubItemInterval, 0);
        addView(mCenterContainer);
    }

    // Get TextView
    private TextView createTextView() {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private ImageView createImageView() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        return imageView;
    }

}
