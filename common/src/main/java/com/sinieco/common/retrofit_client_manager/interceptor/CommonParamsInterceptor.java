package com.sinieco.common.retrofit_client_manager.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
* 通用参数拦截器
* @author LiuHe
* @created at 2021/7/8 17:20
*/
public class CommonParamsInterceptor implements Interceptor {

    /**
     * Header参数Map
     */
    private Map<String, String> headerParamsMap = new HashMap<>();

    /**
     * FormBody参数Map
     */
    private Map<String, Object> commonParamsMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());

        // 添加公共参数
        for (Map.Entry<String, String> params : headerParamsMap.entrySet()) {
            if (!TextUtils.isEmpty(params.getKey()) && !TextUtils.isEmpty(params.getValue())) {
                requestBuilder.header(params.getKey(), params.getValue());
            }
        }

        if (!commonParamsMap.isEmpty()) {
            setCommonParams(oldRequest, requestBuilder);
        }

        Request newRequest = requestBuilder.build();
        Response response = chain.proceed(newRequest);
        return response;
    }

    /**
     * 在Body或Url中添加通用参数
     */
    private void setCommonParams(Request oldRequest, Request.Builder newRequestBuilder) {
        String method = oldRequest.method();
        if (method.equals("POST")) {

            RequestBody body = oldRequest.body();
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;
                FormBody.Builder newFormBuilder = new FormBody.Builder();
                int size = formBody.size();

                for (int i = 0; i < size; i++) {
                    String name = formBody.name(i);
                    String value = formBody.value(i);
                    newFormBuilder.add(name, value);
                }

                for (Map.Entry<String, Object> entry : commonParamsMap.entrySet()) {
                    newFormBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
                }

                FormBody newFormBody = newFormBuilder.build();
                newRequestBuilder.method(oldRequest.method(), newFormBody);
            }

        } else if (method.equals("GET")) {
            HttpUrl url = oldRequest.url();
            HttpUrl.Builder builder = url.newBuilder();
            for (Map.Entry<String, Object> params : commonParamsMap.entrySet()) {
                builder.addQueryParameter(params.getKey(), String.valueOf(params.getValue()));
            }
            newRequestBuilder.url(builder.build());
        }

    }

    /**
     * 添加Header参数
     * @param key 参数名称
     * @param value 参数value
     */
    public CommonParamsInterceptor addHeaderParam(String key, String value) {
        headerParamsMap.put(key, value);
        return this;
    }

    /**
     * 在Body或Url中添加通用参数
     * @param key 参数名称
     * @param value 参数value
     */
    public CommonParamsInterceptor addCommonParam(String key, Object value) {
        commonParamsMap.put(key, value);
        return this;
    }

}
