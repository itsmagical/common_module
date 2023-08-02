package com.sinieco.common.network.interceptor;

import com.sinieco.common.network.NetworkConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
 * 在请求字段为requestGson所对应的请求参数中加入通用参数
 * Created by LiuHe on 2018/9/10.
 */

public class RequestGsonParamInterceptor implements Interceptor {

    /**
     * post或get公共参数 参数
     */
    private Map<String, Object> mCommonParamMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());

        setCommonParams(oldRequest, requestBuilder);

        Request newRequest = requestBuilder.build();
        Response response = chain.proceed(newRequest);

        return response;
    }

    private void setCommonParams(Request oldRequest, Request.Builder requestBuilder) {
        String method = oldRequest.method();
        if (method.equals("POST")) {
            RequestBody body = oldRequest.body();
            if (body instanceof FormBody) {
                requestBuilder.method(oldRequest.method(), oldRequest.body());
                FormBody formBody = (FormBody) body;
                int paramSize = formBody.size();
                int requestGsonNameIndex = -1;
                for (int i = 0; i < paramSize; i++) {
                    String name = formBody.encodedName(i);
                    if (name.equals(NetworkConfig.REQUEST_KEY)) {
                        requestGsonNameIndex = i;
                    }
                }
                if (requestGsonNameIndex > -1) {
                    String value = formBody.encodedValue(requestGsonNameIndex);
                    try {
                        String decodeValue = URLDecoder.decode(value, "utf-8");
                        JSONObject rootJson = new JSONObject(decodeValue);
                        JSONObject paramsJson = rootJson.optJSONObject("data");
                        for (Map.Entry<String, Object> params : mCommonParamMap.entrySet()) {
                            paramsJson.put(params.getKey(), params.getValue());
                        }
                        FormBody newFormBody = new FormBody.Builder()
                                .add(NetworkConfig.REQUEST_KEY, rootJson.toString())
                                .build();
                        requestBuilder.method(oldRequest.method(), newFormBody);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        } else if (method.equals("GET")) {
            HttpUrl url = oldRequest.url();
            HttpUrl.Builder builder = url.newBuilder();
            for (Map.Entry<String, Object> params : mCommonParamMap.entrySet()) {
                builder.addQueryParameter(params.getKey(), String.valueOf(params.getValue()));
            }
            requestBuilder.url(builder.build());
        }
    }

    public RequestGsonParamInterceptor addCommonParam(String key, Object value) {
        mCommonParamMap.put(key, value);
        return this;
    }

}
