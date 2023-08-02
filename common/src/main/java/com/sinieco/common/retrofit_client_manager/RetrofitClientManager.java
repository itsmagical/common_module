package com.sinieco.common.retrofit_client_manager;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RetrofitClient管理类，目的在于解决Retrofit不能改变baseUrl。
 * 使用集合管理RetrofitClient, 每个baseUrl对应一个RetrofitClient
 *
 * Created by LiuHe on 2021/7/15
 */
public class RetrofitClientManager {

    /**
     * Map存储baseUrl对应的RetrofitClient
     */
    private Map<String, RetrofitClient> clientMap = new ConcurrentHashMap<>();

    private String mainBaseUrl;

    private static class SingleTon {
        private static final RetrofitClientManager INSTANCE = new RetrofitClientManager();
    }

    public static RetrofitClientManager getInstance() {
        return SingleTon.INSTANCE;
    }

    /**
     * 创建默认配置的RetrofitClient
     * @return RetrofitClient 网络请求客户端对象
     */
    public RetrofitClient createRetrofitClient(String baseUrl) {
        return createRetrofitClient(baseUrl, null);
    }

    public RetrofitClient createRetrofitClient(String baseUrl, OkHttpOptions options) {
        return  createRetrofitClient(baseUrl, options, false);
    }

    public RetrofitClient createRetrofitClient(String baseUrl, OkHttpOptions options, boolean isMain) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("baseUrl is null");
        }

        RetrofitClient client = clientMap.get(baseUrl);
        if (client != null) {
            throw new IllegalArgumentException("RetrofitClient已创建，不能重复创建");
        }
        client = new RetrofitClient(baseUrl, options);
        clientMap.put(baseUrl, client);

        if (isMain) {
            mainBaseUrl = baseUrl;
        }

        return client;
    }

    public RetrofitClient getRetrofitClient() {
        if (TextUtils.isEmpty(mainBaseUrl)) {
            throw new NullPointerException("请先使用createRetrofitClient(" +
                    "String baseUrl, OkHttpOptions options, boolean isMain) 创建 main client");
        }
        return getRetrofitClient(mainBaseUrl);
    }

    /**
     * 获取baseUrl对应的RetrofitClient
     * @return RetrofitClient 网络请求客户端对象
     */
    public RetrofitClient getRetrofitClient(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("baseUrl is null");
        }
        RetrofitClient client = clientMap.get(baseUrl);
        if (client == null) {
            throw new NullPointerException(
                    "请先使用createRetrofitClient(baseUrl)创建baseUrl对应的client ...");
        }
        return client;
    }

}
