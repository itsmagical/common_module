package com.sinieco.common.network.interceptor;

import com.sinieco.common.network.NetworkConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
 * 新服务通用参数拦截器
 * 在请求字段为requestGson所对应的请求参数中加入通用参数
 * Created by LiuHe on 2018/9/10.
 */
public class NewRequestGsonParamInterceptor implements Interceptor {

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

                FormBody.Builder builder = new FormBody.Builder();

                int gsonKeyIndex = -1;

                for (int i = 0; i < paramSize; i++) {
                    String name = formBody.name(i);
                    String value = formBody.value(i);
                    // builder.add(name, value);

                    String gsonKey = formBody.encodedName(i);
                    if (gsonKey.equals(NetworkConfig.REQUEST_KEY)) {
                        gsonKeyIndex = i;
                    } else {
                        builder.add(name, value);
                    }
                }

                // 在requestGson参数中增加公共参数
                if (gsonKeyIndex > -1) {
                    String value = formBody.encodedValue(gsonKeyIndex);
                    try {
                        String decodeValue = URLDecoder.decode(value, "utf-8");
                        /// 使用JSONTokener判断 json为JSONObject还是JSONArray
                        Object jsonRoot = new JSONTokener(decodeValue).nextValue();
                        if (jsonRoot instanceof JSONObject) {
                            JSONObject jsonObject = (JSONObject) jsonRoot;
                            for (Map.Entry<String, Object> params : mCommonParamMap.entrySet()) {
                                jsonObject.put(params.getKey(), params.getValue());
                            }
                        } else if (jsonRoot instanceof JSONArray) {
                            /// 暂时不在JsonArray中添加公共参数

                        }
                        builder.add(NetworkConfig.REQUEST_KEY, jsonRoot.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                for (Map.Entry<String, Object> params : mCommonParamMap.entrySet()) {
//                    builder.add(params.getKey(), String.valueOf(params.getValue()));
//                }

                FormBody newFormBody = builder.build();

                requestBuilder.method(oldRequest.method(), newFormBody);
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

    public NewRequestGsonParamInterceptor addCommonParam(String key, Object value) {
        mCommonParamMap.put(key, value);
        return this;
    }

}
