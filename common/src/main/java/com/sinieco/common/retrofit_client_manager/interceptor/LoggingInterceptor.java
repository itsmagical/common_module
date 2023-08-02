package com.sinieco.common.retrofit_client_manager.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *
 * Created by LiuHe on 2018/9/11.
 */

public class LoggingInterceptor implements Interceptor {

    private boolean loggable;

    private LoggingInterceptor(Builder builder) {
        this.loggable = builder.loggable;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!loggable) {
            return chain.proceed(request);
        }

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        MediaType contentType = responseBody.contentType();
        String subType = null;
        ResponseBody body;
//        if (contentType != null) {
//            subType = contentType.subtype();
//        }
//        if (subType != null) {
//        } else {
//            return response;
//        }
        String responseBodyString = responseBody.string();
         Log.e("TAG", "intercept: " + responseBodyString );
        body = responseBody.create(contentType, responseBodyString);
        return response.newBuilder().body(body).build();
    }

    public static class Builder {
        private boolean loggable;
        // 是否打印日志
        public Builder loggable(boolean loggable) {
            this.loggable = loggable;
            return this;
        }

        public LoggingInterceptor build() {
            return new LoggingInterceptor(this);
        }
    }

}
