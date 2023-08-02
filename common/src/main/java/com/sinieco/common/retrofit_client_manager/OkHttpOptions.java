package com.sinieco.common.retrofit_client_manager;

import okhttp3.OkHttpClient;


public class OkHttpOptions {

    /**
     * 是否启用cookie
     * true：启用; 默认false
     */
    private boolean cookieEnable;

    private OkHttpClient.Builder okHttpBuilder;

    /**
     * 是否启用Cookie
     */
    public OkHttpOptions cookieEnable(boolean enable) {
        cookieEnable = enable;
        return this;
    }

    /**
     * 初始化OkHttp配置，不设置则使用默认配置
     */
    public OkHttpOptions setOkHttpBuilder(OkHttpClient.Builder builder) {
        this.okHttpBuilder = builder;
        return this;
    }

    public boolean getCookieEnable() {
        return cookieEnable;
    }

    public OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpBuilder;
    }

}
