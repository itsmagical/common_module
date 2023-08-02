package com.sinieco.common.widget.multiplestatus;

/**
 * 状态布局配置
 * Created by LiuHe on 2019/8/17.
 */

public class MultipleStatusConfig {

    static final int MAX_RETRYING_INTERVAL = 2000;

    /**
     * 重试间隔时间
     */
    private long retryingInterval = MAX_RETRYING_INTERVAL;

    /**
     * 状态布局配置， 优先级：默认配置 < 全局配置 < 局部配置
     */
    private StatusViewProvider statusViewProvider;

    private MultipleStatusConfig() {

    }

    private static class SingleTon {
        static MultipleStatusConfig INSTANCE = new MultipleStatusConfig();
    }

    public static MultipleStatusConfig getInstance() {
        return SingleTon.INSTANCE;
    }

    public MultipleStatusConfig setRetryingInterval(long interval) {
        interval = interval > MAX_RETRYING_INTERVAL ? MAX_RETRYING_INTERVAL : interval;
        retryingInterval = interval;
        return this;
    }

    long getRetryingInterval() {
        return retryingInterval;
    }

    public void setStatusViewProvider(StatusViewProvider provider) {
        statusViewProvider = provider;
    }

    public StatusViewProvider getStatusViewProvider() {
        return statusViewProvider;
    }

}
