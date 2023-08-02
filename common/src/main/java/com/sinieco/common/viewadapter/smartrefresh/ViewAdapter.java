package com.sinieco.common.viewadapter.smartrefresh;

import androidx.databinding.BindingAdapter;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sinieco.arch.binding.command.BindingCommand;
import com.sinieco.common.util.ResUtils;

/**
 *
 * Created by LiuHe on 2018/9/26.
 */

public class ViewAdapter {

    @BindingAdapter(value = {"onRefreshCommand", "autoRefresh"}, requireAll = false)
    public static void setSmartRefreshLayout(SmartRefreshLayout smartRefreshLayout, final BindingCommand refreshCommand, boolean autoRefresh) {
        ClassicsHeader header = new ClassicsHeader(smartRefreshLayout.getContext());
//        header.setPrimaryColor(smartRefreshLayout.getContext().getResources().getColor(R.color.navigation_bar_background));
//        header.setAccentColor(Color.parseColor("#FFFFFF"));
        header.setEnableLastTime(false);
        smartRefreshLayout.setRefreshHeader(header);
        if (autoRefresh) {
            smartRefreshLayout.autoRefresh(400);
        }
        smartRefreshLayout.setEnableOverScrollDrag(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (refreshCommand != null) {
                    refreshCommand.execute();
                }
            }
        });
    }

    @BindingAdapter(value = {"onLoadMoreCommand"}, requireAll = false)
    public static void setOnLoadMore(SmartRefreshLayout smartRefreshLayout, final BindingCommand loadMoreCommand) {
        smartRefreshLayout.setEnableOverScrollDrag(true);
        ClassicsFooter footer = new ClassicsFooter(smartRefreshLayout.getContext());
        smartRefreshLayout.setRefreshFooter(footer);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (loadMoreCommand != null) {
                    loadMoreCommand.execute();
                }
            }
        });
    }

    @BindingAdapter(value = {"headerPrimaryColor", "headerAccentColor"}, requireAll = false)
    public static void setSmartRefreshColor(SmartRefreshLayout smartRefreshLayout, int headerPrimaryColor, int headerAccentColor) {
        RefreshHeader refreshHeader = smartRefreshLayout.getRefreshHeader();
        if (refreshHeader instanceof ClassicsHeader) {
            if (headerPrimaryColor > 0) {
                ((ClassicsHeader) refreshHeader).setPrimaryColors(ResUtils.getColor(headerPrimaryColor));
            }
        }
    }

    @BindingAdapter(value = {"stopRefresh"}, requireAll = false)
    public static void stopRefresh(SmartRefreshLayout smartRefreshLayout, Boolean stopRefresh) {
        if (stopRefresh != null) {
            if (smartRefreshLayout.isRefreshing()) {
                smartRefreshLayout.finishRefresh();
            } else {
                smartRefreshLayout.finishLoadMore();
            }
        }
    }
}
