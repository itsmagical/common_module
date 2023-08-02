package com.sinieco.common.retrofit_client_manager;

import com.sinieco.common.retrofit_client_manager.cookie.CookieJarImpl;
import com.sinieco.common.retrofit_client_manager.cookie.MemoryCookieStore;
import com.sinieco.common.retrofit_client_manager.interceptor.CommonParamsInterceptor;
import com.sinieco.common.retrofit_client_manager.interceptor.LoggingInterceptor;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit的配置包装类
 * @author LiuHe
 * @created at 2021/7/15 11:16
 */
public class RetrofitClient {

    private Retrofit retrofit;

    private String baseUrl;

    private OkHttpClient okHttpClient;

    private OkHttpOptions okHttpOptions;

    private CommonParamsInterceptor commonParamsInterceptor;

    public RetrofitClient(String baseUrl) {
        this(baseUrl, null);
    }

    public RetrofitClient(String baseUrl, OkHttpOptions options) {
        this.baseUrl = baseUrl;
        if (options == null) {
            options = new OkHttpOptions();
        }
        okHttpOptions = options;
        commonParamsInterceptor = new CommonParamsInterceptor();

        createRetrofit();
    }

    /**
     * 创建API接口对象
     */
    public <T> T create(Class<T> service) {
        if (service == null) {
            throw new NullPointerException("api service is null");
        }

        return retrofit.create(service);
    }

    /**
     * 创建Retrofit
     */
    private void createRetrofit() {
        OkHttpClient.Builder clientBuilder = okHttpOptions.getOkHttpBuilder();
        if (clientBuilder == null) {
            clientBuilder = new OkHttpClient.Builder();
        }

        clientBuilder.addInterceptor(commonParamsInterceptor);

        // 日志拦截器
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder().loggable(true).build();
        clientBuilder.addInterceptor(loggingInterceptor);

        if (okHttpOptions.getCookieEnable()) {
            clientBuilder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        }

        okHttpClient = clientBuilder.build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    /**
     * 获取baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 获取OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 通用参数拦截器
     */
    public CommonParamsInterceptor getCommonParamsInterceptor() {
        return commonParamsInterceptor;
    }

    /**
     * 获取拦截器集合
     */
    public List<Interceptor> getInterceptors() {
        if (okHttpClient != null) {
            return okHttpClient.interceptors();
        }
        return null;
    }

}
