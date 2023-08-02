package com.sinieco.common.widget.tabbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import static androidx.annotation.Dimension.PX;
import static androidx.annotation.Dimension.SP;


import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinieco.common.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by L.H on 2019/5/29.
 */

public class TopTabBar extends FrameLayout {

    public static final String TOP_TAB_BAR_FRAGMENT_EXTRA = "top_tab_bar_fragment_extra";
    private static final String TAG = "TopTabBar";

    /**
     *  未设置tab宽度时的最大可见tab数，超过可滑动
     */
    private static final int MAX_WEIGHT_TAB_COUNT = 5;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private LayoutInflater layoutInflater;
    private LinearLayout mTabsContainer;
    private @LayoutRes
    int mTabViewRes;
    private ViewGroup mTabView;
    private int paddingTop;
    private int paddingMiddle;
    private int paddingBottom;
    public Map<View, Fragment> fragmentMap;
    public List<View> tabViews;
    private View contentContainer;
    private Fragment preFragment;
    private View preTabView;
    private int tabViewPosition;
    private int preTabViewPosition;

    private int normalColorId;
    private int selectedColorId;
    private int imgNormalColorId = -1;
    private int imgSelectedColorId = -1;

    private int mFontSize;
    private boolean isFixedWidth;
    private int mTabViewWidth;
    private int mMaxWeightTabCount = MAX_WEIGHT_TAB_COUNT;
    private int mIndicatorLineWidth;
    private int mIndicatorLineHeight;
    private View mTabIndicatorLine;
    private int mIndicatorLineLeft;
    private int tabViewWidthAttr;
    private int width;
    private HorizontalScrollView topTabScrollView;
    private View mRlTabUnderline;
    private boolean underLineDevision;

    public TopTabBar(Context context) {
        this(context, null);
    }

    public TopTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        initAttributes(attrs);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TopTabBar);
        tabViewWidthAttr = (int) typedArray.getDimension(R.styleable.TopTabBar_tabViewWidth, 0);
        typedArray.recycle();
    }

    public TopTabBar init(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
        View topTabBarView = layoutInflater.inflate(R.layout.view_top_tab_bar_frame, this);
        mTabsContainer = (LinearLayout) topTabBarView.findViewById(R.id.tabs_container);
        contentContainer = topTabBarView.findViewById(R.id.content_container);
        topTabScrollView = (HorizontalScrollView) topTabBarView.findViewById(R.id.top_tab_scroll_view);
        mRlTabUnderline = topTabBarView.findViewById(R.id.rl_tab_underline);
        mTabViewRes = R.layout.view_top_tab_view;
        mTabIndicatorLine = topTabBarView.findViewById(R.id.tab_indicator_line);
        mTabIndicatorLine.setVisibility(View.INVISIBLE);
        fragmentMap = new HashMap<>();
        tabViews = new ArrayList<>();
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        if (null != fragmentMap && fragmentMap.size() > 0) {
            if (tabViewWidthAttr > 0) {
                mTabViewWidth = tabViewWidthAttr;
            } else {
                int size = fragmentMap.size() > mMaxWeightTabCount ? mMaxWeightTabCount : fragmentMap.size();
                mTabViewWidth = width / size;
            }
            int left = (mTabViewWidth - mIndicatorLineWidth) / 2;
            mIndicatorLineLeft = left > 0 ? left : 0;
            if (!isFixedWidth) {
                setTabViewWidthAfterMeasure();
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int tabCount = mTabsContainer.getChildCount();
        if (tabCount > 0 && tabCount > mMaxWeightTabCount) {
//            ViewGroup.LayoutParams layoutParams = topTabScrollView.getLayoutParams();
//            layoutParams.width = mTabViewWidth * tabCount;
            ViewGroup.LayoutParams layoutParams = mTabsContainer.getLayoutParams();
            layoutParams.width = mTabViewWidth * tabCount;
        }
        if (null != mTabIndicatorLine) {
            int tabIndicatorLineTop = mTabIndicatorLine.getTop();
            mTabIndicatorLine.layout(
                    mIndicatorLineLeft,
                    tabIndicatorLineTop,
                    mIndicatorLineLeft + mIndicatorLineWidth,
                    tabIndicatorLineTop + mIndicatorLineHeight
            );
        }
        if (underLineDevision) {
            setTabIndicatorUnderlineSize(mTabViewWidth, mIndicatorLineHeight);
        }
    }

    public TopTabBar addTab(String tabName, @DrawableRes int drawableResId, Class fragmentClass) {
        addTab(tabName, drawableResId, fragmentClass, null);
        return this;
    }

    public <T> TopTabBar addTab(String tabName, @DrawableRes int drawableResId, Class fragmentClass, T extra) {
        View tabView = layoutInflater.inflate(mTabViewRes, null, false);
        setTabView(tabView, tabName, drawableResId);
        addTabView(tabView);
        tabView.setTag(tabViewPosition++);
        Fragment fragmentInstance = getFragmentInstance(fragmentClass);
        setExtra(fragmentInstance, extra);
        fragmentMap.put(tabView, fragmentInstance);
        tabViews.add(tabView);
        if (null != fragmentInstance && fragmentMap.size() == 1) {
            mFragmentManager
                    .beginTransaction()
                    .add(contentContainer.getId(), fragmentInstance)
                    .commitAllowingStateLoss();
            preFragment = fragmentInstance;
            tabView.setSelected(true);
            preTabView = tabView;
            callBackOnAppear(fragmentInstance);
        }
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentMap.size() > mMaxWeightTabCount || isFixedWidth) {
                    calcRelativeWindowDistance(v);
                }

                Fragment fragment = fragmentMap.get(v);
                if (null != fragment && fragment != preFragment) {
                    beginTransaction(fragment, (Integer) v.getTag());
                    preFragment = fragment;
                    //int dxPosition = (Integer) v.getTag() - (Integer) preTabView.getTag();
                    setIndicatorLineAnimator((Integer) v.getTag());
                    v.setSelected(true);
                    preTabView.setSelected(false);
                    preTabView = v;
                }
                if (indexListener != null) {
                    int index = mTabsContainer.indexOfChild(v);
                    indexListener.onTabSelectedIndex(index);
                }
            }
        });

        if (mTabIndicatorLine != null) {
            int visibility = mTabIndicatorLine.getVisibility();
            if (visibility == INVISIBLE) {
                mTabIndicatorLine.setVisibility(VISIBLE);
            }
        }
        return this;
    }

    public Fragment getCurrentFragment() {
        return preFragment;
    }

    private void calcRelativeWindowDistance(View directionChildView) {

        int relativeLeftWindowDistance = directionChildView.getLeft() - topTabScrollView.getScrollX();
        if (topTabScrollView.getScrollX() != 0 && relativeLeftWindowDistance <= directionChildView.getWidth()) {
            int slidingDistance = directionChildView.getWidth() - relativeLeftWindowDistance;
            topTabScrollView.smoothScrollBy(-slidingDistance, 0);
        } else if (this.width - relativeLeftWindowDistance - directionChildView.getWidth() <= directionChildView.getWidth()) {
            int slidingDistance = directionChildView.getWidth() - (this.width - relativeLeftWindowDistance - directionChildView.getWidth());
            topTabScrollView.smoothScrollBy(slidingDistance, 0);
        }

    }

    private void setTabView(View tabView, String tabName, int drawableResId) {
        ImageView tabViewIc = (ImageView) tabView.findViewById(R.id.tab_view_ic);
        TextView tabViewName = (TextView) tabView.findViewById(R.id.tab_view_name);
        if (null != tabViewIc && drawableResId > 0) {
            Drawable drawableIcon = ContextCompat.getDrawable(mContext, drawableResId);
            if (imgNormalColorId != -1 && imgSelectedColorId != -1) {
                tabViewIc.setImageDrawable(TintUtil.setStateListTintColorId(mContext, drawableIcon, imgNormalColorId, imgSelectedColorId));
            } else {
                tabViewIc.setImageDrawable(TintUtil.setStateListTintColorId(mContext, drawableIcon, normalColorId, selectedColorId));
            }
            tabViewIc.setPadding(0, 0, paddingMiddle, 0);
        }
        if (null != tabViewName) {
            tabViewName.setText(tabName);
            tabViewName.setTextSize(mFontSize);
            int[][] states = new int[3][];
            states[0] = new int[]{android.R.attr.state_pressed};
            states[1] = new int[]{android.R.attr.state_selected};
            states[2] = new int[]{};
            int[] colors = new int[]{ContextCompat.getColor(mContext, selectedColorId),
                    ContextCompat.getColor(mContext, selectedColorId),
                    ContextCompat.getColor(mContext, normalColorId)};
            ColorStateList colorStateList = new ColorStateList(states, colors);
            tabViewName.setTextColor(colorStateList);
        }
        tabView.setPadding(0, paddingTop, 0, paddingBottom);
    }

    private void beginTransaction(Fragment fragment, int curTabViewPosition) {
        if (preFragment == fragment) {
            return;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (curTabViewPosition > preTabViewPosition) {
            transaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.slide_out
            );
        } else {
            transaction.setCustomAnimations(
                    R.anim.slide_pop_in,
                    R.anim.slide_pop_out
            );
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(contentContainer.getId(), fragment);
        }
        if (preFragment != null) {
            transaction.hide(preFragment);
        }
        transaction.commitAllowingStateLoss();
        preTabViewPosition = curTabViewPosition;

        callBackOnAppear(fragment);
    }

    private void callBackOnAppear(final Fragment fragment) {
        if (fragment instanceof OnAppearListener) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((OnAppearListener) fragment).onAppear();
                }
            }, 200);
        }
    }

    private void addTabView(View tabView) {
        LinearLayout.LayoutParams params;
        if (mTabViewWidth == 0) {
//            params = new LinearLayout.LayoutParams(
//                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = mTabViewWidth;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        mTabsContainer.addView(tabView, params);
//        tabView.measure(0, 0);
//        ViewGroup.LayoutParams layoutParams = topTabScrollView.getLayoutParams();
//        layoutParams.height = tabView.getMeasuredHeight();
    }

    private void setTabViewWidthAfterMeasure() {
        for (View tabView : fragmentMap.keySet()) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
            params.width = (mTabViewWidth);
        }
    }

    private void setIndicatorLineAnimator(int dxPosition) {
        int to = dxPosition * mIndicatorLineWidth + mIndicatorLineLeft * (dxPosition * 2);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTabIndicatorLine, "translationX", to);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(200);
        animator.start();
    }

    private void setIndicatorLine(int dxPosition) {
        int to = dxPosition * mTabViewWidth;
        //int to = dxPosition * mIndicatorLineWidth + mIndicatorLineLeft * (dxPosition * 2);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTabIndicatorLine, "translationX", to);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(0);
        animator.start();
    }

    /**
     *  设置TabView布局
     *  默认 com.sn.common.R.layout.view_top_tab_view
     */
    public TopTabBar setTabViewRes(@LayoutRes int layoutResId) {
        mTabViewRes = layoutResId;
        return this;
    }

    public TopTabBar setTabViewWidth(int tabViewWidth) {
        tabViewWidthAttr = tabViewWidth;
        mTabViewWidth = tabViewWidth;
        isFixedWidth = true;
        return this;
    }

    public TopTabBar setWeightTabCount(int tabCount) {
        mMaxWeightTabCount = tabCount;
        return this;
    }

    public TopTabBar setPadding(int paddingTop, int paddingMiddle, int paddingBottom) {
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingMiddle = paddingMiddle;
        return this;
    }

    public TopTabBar setFontSize(@Dimension(unit = Dimension.SP) int fontSize) {
        mFontSize = fontSize;
        return this;
    }

    public TopTabBar setChangeColor(@ColorRes int normalColor, @ColorRes int selectedColor) {
//        this.normalColorId = normalColor;
//        this.selectedColorId = selectedColor;
        setChangeColor(normalColor, selectedColor, normalColor, selectedColor);
        return this;
    }

    public TopTabBar setChangeColor(@ColorRes int normalColor, @ColorRes int selectedColor,
                                    @ColorRes int imgNormalColor, @ColorRes int imgSelectedColor) {
        this.normalColorId = normalColor;
        this.selectedColorId = selectedColor;
        this.imgNormalColorId = imgNormalColor;
        this.imgSelectedColorId = imgSelectedColor;
        return this;
    }

    public TopTabBar setTabIndicatorUnderlineDevesion(@Dimension(unit = PX) int height) {
        underLineDevision = true;
        mIndicatorLineHeight = height;
        return this;
    }

    public TopTabBar setTabIndicatorUnderlineSize(@Dimension(unit = PX) int width, @Dimension(unit = PX) int height) {
        mIndicatorLineWidth = width;
        mIndicatorLineHeight = height;
//        mTabIndicatorLine = findViewById(R.id.tab_indicator_line);
//        mTabIndicatorLine.setVisibility(View.INVISIBLE);
        ViewGroup.LayoutParams params = mTabIndicatorLine.getLayoutParams();
        params.width = width;
        params.height = height;
        mTabIndicatorLine.setLayoutParams(params);
        return this;
    }

    public TopTabBar setTabIndicatorUnderlineDrawableRes(@DrawableRes int drawableResId) {
        View tabIndicatorLine = findViewById(R.id.tab_indicator_line);
        tabIndicatorLine.setBackgroundResource(drawableResId);
        return this;
    }

//    public TopTabBar setTabShadowStyle(ShadowLayout.Style style) {
//        ShadowLayout shadowLayout = findViewById(R.id.tabs_shadow_layout);
//        shadowLayout.setShadowStyle(style);
//        return this;
//    }

    public TopTabBar setUnderlineVisible(boolean isVisible) {
        if (isVisible) {
            mRlTabUnderline.setVisibility(VISIBLE);
        } else {
            mRlTabUnderline.setVisibility(GONE);
        }
        return this;
    }

    public void setCurrentPage(int position) {
        int count = mTabsContainer.getChildCount();
        View v = mTabsContainer.getChildAt(position);
//        setIndicatorLineAnimator((Integer) v.getTag());
//        if (tabViewWidthAttr > 0 ) {
//            calcRelativeWindowDistance(v);
//        }
        Fragment fragment = fragmentMap.get(v);
        if (null != fragment && fragment != preFragment) {
            beginTransaction(fragment, (Integer) v.getTag());
            preFragment = fragment;
            //int dxPosition = (Integer) v.getTag() - (Integer) preTabView.getTag();
//            setIndicatorLineAnimator2((Integer) v.getTag());
            v.setSelected(true);
            preTabView.setSelected(false);
            preTabView = v;
        }
        final Integer tag = (Integer) v.getTag();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setIndicatorLine(tag);
                int i = (tag - 1) * mTabViewWidth;
                topTabScrollView.scrollTo(i, 0);
            }
        }, 200);
    }

    public void clearTab() {
        mTabsContainer.removeAllViews();
        fragmentMap.clear();
        tabViewPosition = 0;
        preTabView = null;
    }

    private <T> void setExtra(Fragment fragment, T extra) {
        if (null != extra) {
            Bundle bundle = new Bundle();
            if (extra instanceof Integer) {
                bundle.putInt(TOP_TAB_BAR_FRAGMENT_EXTRA, (Integer) extra);
            } else if (extra instanceof String) {
                bundle.putString(TOP_TAB_BAR_FRAGMENT_EXTRA, (String) extra);
            } else if (extra instanceof Boolean) {
                bundle.putBoolean(TOP_TAB_BAR_FRAGMENT_EXTRA, (Boolean) extra);
            } else if (extra instanceof Serializable) {
                bundle.putSerializable(TOP_TAB_BAR_FRAGMENT_EXTRA, (Serializable) extra);
            }
            fragment.setArguments(bundle);
        }

    }

    private Fragment getFragmentInstance(Class fragmentClass) {
        String fragmentName = fragmentClass.getName();
        try {
            Class<?> aClass = Class.forName(fragmentName);
            return (Fragment) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private OnTabSelectedIndexListener indexListener;

    public void setOnTabSelectedIndexListener(OnTabSelectedIndexListener listener) {
        indexListener = listener;
    }



}
