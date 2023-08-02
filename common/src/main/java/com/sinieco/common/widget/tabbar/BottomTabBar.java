package com.sinieco.common.widget.tabbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinieco.common.R;

import java.util.HashMap;
import java.util.Map;

public class BottomTabBar extends LinearLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    private FragmentManager mFragmentManager;
    private @IdRes
    int containerId;
    private int mFontSize;
    private int mNormalColor;
    private int mSelectedColor;

    private int mTopPadding;
    private int mMiddlePadding;
    private int mBottomPadding;

    private View mSelectedView;
    private Fragment mSelectedFragment;

    private Map<View, Fragment> tabMaps = new HashMap<>();

    public BottomTabBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(HORIZONTAL);
        mInflater = LayoutInflater.from(context);
    }

    private int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            layoutParams.width = width / childCount;
        }
    }

    public BottomTabBar init(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        return this;
    }

    public BottomTabBar setContainer(@IdRes int containerId) {
        this.containerId = containerId;
        return this;
    }

    public BottomTabBar setFontSize(@Dimension(unit = Dimension.SP) int fontSize) {
        mFontSize = fontSize;
        return this;
    }

    public BottomTabBar setColors(int normalColor, int selectedColor) {
        mNormalColor = normalColor;
        mSelectedColor = selectedColor;
        return this;
    }

    public BottomTabBar setPadding(int topPadding, int middlePadding, int bottomPadding) {
        mTopPadding = topPadding;
        mMiddlePadding = middlePadding;
        mBottomPadding = bottomPadding;
        return this;
    }

    public BottomTabBar addTab(String tabName, @DrawableRes int drawableResId, Class fragmentClass) {
        View mTabView = mInflater.inflate(R.layout.view_bottom_tab, null);
        setTabView(mTabView, tabName, drawableResId);
        addView(mTabView);
        Fragment fragmentInstance = getFragmentInstance(fragmentClass);
        tabMaps.put(mTabView, fragmentInstance);
        if (null != fragmentInstance && tabMaps.size() == 1) {
            mFragmentManager
                    .beginTransaction()
                    .add(containerId, fragmentInstance)
                    .commitAllowingStateLoss();
            mSelectedView = mTabView;
            mSelectedFragment = fragmentInstance;
        }
        mTabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = tabMaps.get(v);
                if (fragment != null && fragment != mSelectedFragment) {
                    beginTransation(v, fragment);
                    mSelectedView = v;
                    mSelectedFragment = fragment;
                }
            }
        });
        return this;
    }

    private void beginTransation(View view, Fragment fragment) {
        if (mSelectedFragment == null || fragment == null) return;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(containerId, fragment);
        }
        if (mSelectedFragment != null) {
            transaction.hide(mSelectedFragment);
        }
        transaction.hide(mSelectedFragment);
    }

    private void setTabView(View tabView, String tabName, int drawableResId) {
        ImageView tabViewIc = (ImageView) tabView.findViewById(R.id.iv_bottom_tab_ic);
        TextView tabViewName = (TextView) tabView.findViewById(R.id.tv_bottom_tab_name);
        if (null != tabViewIc && drawableResId > 0) {
            Drawable drawableIcon = ContextCompat.getDrawable(mContext, drawableResId);
            tabViewIc.setImageDrawable(TintUtil.setStateListTintColor(drawableIcon, mNormalColor, mSelectedColor));
            tabViewIc.setPadding(0, 0, mMiddlePadding, 0);
        }
        if (null != tabViewName) {
            tabViewName.setText(tabName);
            tabViewName.setTextSize(mFontSize);
            int[][] states = new int[3][];
            states[0] = new int[]{android.R.attr.state_pressed};
            states[1] = new int[]{android.R.attr.state_selected};
            states[2] = new int[]{};
//            int[] colors = new int[]{ContextCompat.getColor(mContext, selectedColorId),
//                    ContextCompat.getColor(mContext, selectedColorId),
//                    ContextCompat.getColor(mContext, normalColorId)};
            int[] colors = new int[]{mSelectedColor, mSelectedColor, mNormalColor};
            ColorStateList colorStateList = new ColorStateList(states, colors);
            tabViewName.setTextColor(colorStateList);
        }
        tabView.setPadding(0, mTopPadding, 0, mBottomPadding);
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

}
