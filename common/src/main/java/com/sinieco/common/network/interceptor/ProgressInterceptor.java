package com.sinieco.common.network.interceptor;


import com.sinieco.common.network.download.ProgressResponseBody;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *
 * Created by LiuHe on 2017/5/10.
 */

public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }
}
