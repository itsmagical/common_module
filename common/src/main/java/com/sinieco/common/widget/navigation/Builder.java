package com.sinieco.common.widget.navigation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static androidx.annotation.Dimension.SP;
import static androidx.annotation.Dimension.DP;

/**
 * Created by admin on 2019/5/26.
 */
public class Builder {

    private static final int INVALIDATE = -1;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_DIVIDING_LINE_HEIGHT = 0;
    private static final int DEFAULT_DIVIDING_LINE_COLOR = Color.GRAY;
    private static final int DEFAULT_TITLE_GRAVITY = Gravity.CENTER | Gravity.TOP;

    private Context mContext;
    private ViewGroup mContentParent;
    private Style mStyle = Style.DEFAULT;
    private int mMinimumHeight = INVALIDATE;
    private int mSubItemInterval = INVALIDATE;
    private int mTitleGravity = DEFAULT_TITLE_GRAVITY;
    private int mBackgroundDrawableResId = INVALIDATE;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private int mDividingLineColor = DEFAULT_DIVIDING_LINE_COLOR;
    private int mDividingLineHeight = DEFAULT_DIVIDING_LINE_HEIGHT;

    private TextViewOptions mTitleTextOps;
    private ImageViewOptions mImageOps;
    private List<Entity> mTitleEntities = new ArrayList<>();
    private List<Entity> mLeftMenuEntities = new ArrayList<>();
    private List<Entity> mRightMenuEntities = new ArrayList<>();

    Builder(Activity activity) {
        mContext = activity;
        mContentParent = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
    }

    Builder(View contentView) {
        if (contentView instanceof LinearLayout) {
            if (((LinearLayout) contentView).getOrientation() == LinearLayout.VERTICAL) {
                mContext = contentView.getContext();
                mContentParent = (ViewGroup) contentView;
            } else {
                throw new IllegalArgumentException(
                        "please ensure contentView's orientation is LinearLayout.VERTICAL");
            }
        } else {
            throw new IllegalArgumentException("please ensure contentView instanceof LinearLayout");
        }
    }

    public Builder setStatusBarStyle(Style style) {
        mStyle = style;
        return this;
    }

    public Builder setMinimumHeight(@Dimension(unit = DP) int minimumHeight) {
        mMinimumHeight = minimumHeight;
        return this;
    }

    public Builder setSubItemInterval(@Dimension(unit = DP) int subItemInterval) {
        mSubItemInterval = subItemInterval;
        return this;
    }

    public Builder setBackgroundColor(@ColorInt int color) {
        mBackgroundColor = color;
        return this;
    }

    public Builder setBackgroundColorRes(@ColorRes int colorRes) {
        mBackgroundColor = ContextCompat.getColor(mContext, colorRes);
        return this;
    }

    public Builder setBackgroundDrawableRes(@DrawableRes int drawableResId) {
        mBackgroundDrawableResId = drawableResId;
        return this;
    }

    public Builder setDividingLineHeight(@Dimension(unit = DP) int dividingLineHeight) {
        mDividingLineHeight = dividingLineHeight;
        return this;
    }

    public Builder setDividingLineColor(@ColorInt int color) {
        mDividingLineColor = color;
        return this;
    }

    public Builder setDividingLineColorRes(@ColorRes int colorResId) {
        mDividingLineColor = ContextCompat.getColor(mContext, colorResId);
        return this;
    }

    public Builder setTitleGravity(int gravity) {
        mTitleGravity = gravity;
        return this;
    }

    public Builder setTitleText(String titleText) {
        setTitleText(titleText, TextViewOptions.DEFAULT_TITLE_TEXT_SIZE);
        return this;
    }

    public Builder setTitleText(String titleText, @Dimension(unit = SP) int titleTextSize) {
        setTitleText(titleText, titleTextSize, TextViewOptions.DEFAULT_TEXT_COLOR);
        return this;
    }

    public Builder setTitleText(String titleText, @Dimension(unit = SP) int titleTextSize, @ColorInt int titleColor) {
        setTitleText(
                TextViewOptions
                        .Builder()
                        .setText(titleText)
                        .setTextSize(titleTextSize)
                        .setTextColor(titleColor)
                        .build()
        );
        return this;
    }

    public Builder setTitleText(TextViewOptions ops) {
        mTitleTextOps = ops;
        return this;
    }

    public Builder setTitleView(@Nullable View view) {
        setTitleView(view, null);
        return this;
    }

    public Builder setTitleView(@Nullable View view, Options ops) {
        mTitleEntities.add(new Entity(view, ops));
        return this;
    }

//    public Builder addBackIcon(@DrawableRes int drawableResId) {
//        addLeftMenuImage(
//                ImageViewOptions
//                        .Builder()
//                        .setDrawableResId(drawableResId)
//                        .setListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (mContext instanceof Activity) {
//                                    ((Activity) mContext).finish();
//                                }
//                            }
//                        })
//                        .build()
//        );
//        return this;
//    }

    public Builder addBackIcon(@DrawableRes int drawableResId) {
        addBackIcon(drawableResId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof Activity) {
                    ((Activity) mContext).finish();
                }
            }
        });
        return this;
    }

    public Builder addBackIcon(@DrawableRes int drawableResId, View.OnClickListener listener) {
        addLeftMenuImage(
                ImageViewOptions
                        .Builder()
                        .setDrawableResId(drawableResId)
                        .setWidthWithoutPadding(Utils.dp2px(mContext, 12))
                        .setHeightWithoutPadding(Utils.dp2px(mContext, 19))
                        .setPaddingLeft(Utils.dp2px(mContext, 10))
                        .setListener(listener)
                        .build()
        );
        return this;
    }

    public Builder addLeftMenuText(@Nullable TextViewOptions ops) {
        addLeftMenuView(null, ops);
        return this;
    }

    public Builder addLeftMenuImage(@Nullable ImageViewOptions ops) {
        addLeftMenuView(null, ops);
        return this;
    }

    public Builder addLeftMenuView(@Nullable View view) {
        addLeftMenuView(view, null);
        return this;
    }

    public Builder addLeftMenuView(@Nullable View view, Options ops) {
        mLeftMenuEntities.add(new Entity(view, ops));
        return this;
    }

    public Builder addRightMenuText(@Nullable TextViewOptions ops) {
        addRightMenuView(null, ops);
        return this;
    }

    public Builder addRightMenuImage(@Nullable ImageViewOptions ops) {
        addRightMenuView(null, ops);
        return this;
    }

    public Builder addRightMenuView(@Nullable View view) {
        addRightMenuView(view, null);
        return this;
    }

    public Builder addRightMenuView(@Nullable View view, Options ops) {
        mRightMenuEntities.add(new Entity(view, ops));
        return this;
    }

    private NavigationBar build() {
        NavigationBar navigationBar = new NavigationBar(mContext);
        completion(navigationBar);
        return navigationBar;
    }

    public NavigationBar apply() {
        final NavigationBar navigationBar = build();
        mContentParent.addView(navigationBar, 0);
        navigationBar.post(new Runnable() {
            @Override
            public void run() {
                adjustLayout(navigationBar);
            }
        });
        return navigationBar;
    }

    private void completion(NavigationBar navigationBar) {
        navigationBar.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (INVALIDATE != mMinimumHeight) {
            navigationBar.setMinimumHeight(mMinimumHeight);
        }
        if (INVALIDATE != mSubItemInterval) {
            navigationBar.setSubItemInterval(mSubItemInterval);
        }

        if (Style.DEFAULT != mStyle) {
            navigationBar.setStatusBarStyle(mStyle);
        }

        navigationBar.setBackgroundColor(mBackgroundColor);
        if (INVALIDATE != mBackgroundDrawableResId) {
            navigationBar.setBackgroundResource(mBackgroundDrawableResId);
        }

        navigationBar.setTitleGravity(mTitleGravity);
        if (null != mTitleTextOps) {
            navigationBar.setTitleText(mTitleTextOps);
        }
        // set title
        if (Utils.isNotEmpty(mTitleEntities)) {
            for (Entity titleEntity : mTitleEntities) {
                navigationBar.addTitleView(titleEntity.view, titleEntity.ops);
            }
        }
        // set left menu
        if (Utils.isNotEmpty(mLeftMenuEntities)) {
            for (Entity leftItem : mLeftMenuEntities) {
                if (null != leftItem.view && null != leftItem.ops) {
                    navigationBar.addLeftMenuView(leftItem.view, leftItem.ops);
                } else if (null != leftItem.ops) {
                    if (leftItem.ops instanceof TextViewOptions) {
                        navigationBar.addLeftMenuText((TextViewOptions) leftItem.ops);
                    } else if (leftItem.ops instanceof ImageViewOptions) {
                        navigationBar.addLeftMenuImage((ImageViewOptions) leftItem.ops);
                    } else {
                        throw new NullPointerException("");
                    }
                } else if (null != leftItem.view) {
                    navigationBar.addLeftMenuView(leftItem.view);
                } else {
                    throw new NullPointerException("please ensure ops or view at least one nonnull");
                }
            }
        }

        // set right menu
        if (Utils.isNotEmpty(mRightMenuEntities)) {
            for (Entity rightItem : mRightMenuEntities) {
                View menuView = rightItem.view;
                Options ops = rightItem.ops;
                if (null != rightItem.view && null != rightItem.ops) {
                    navigationBar.addRightView(menuView, ops);
                } else if (null != rightItem.ops) {
                    if (rightItem.ops instanceof TextViewOptions) {
                        navigationBar.addRightMenuText((TextViewOptions) rightItem.ops);
                    } else if (rightItem.ops instanceof ImageViewOptions) {
                        navigationBar.addRightMenuImage((ImageViewOptions) rightItem.ops);
                    } else {
                        throw new NullPointerException("U setup ops cannot support");
                    }
                } else if (null != rightItem.view) {
                    navigationBar.addRightView(rightItem.view);
                } else {
                    throw new NullPointerException("please ensure ops or view at least one nonnull");
                }
            }
        }

    }

    private void adjustLayout(NavigationBar navigationBar) {
        if (null != mContentParent && !(mContentParent instanceof LinearLayout)) {
            ViewGroup.MarginLayoutParams params =
                    (ViewGroup.MarginLayoutParams) mContentParent.getChildAt(1).getLayoutParams();
            params.topMargin += navigationBar.getHeight();
            mContentParent.getChildAt(1).setLayoutParams(params);
        }
    }

    private static class Entity {

        View view;
        Options ops;

        Entity(View view, Options ops) {
            this.view = view;
            this.ops = ops;
        }

    }


}
