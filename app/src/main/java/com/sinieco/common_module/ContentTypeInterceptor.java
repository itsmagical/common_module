package com.sinieco.common_module;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class ContentTypeInterceptor implements Interceptor {

    public static final MediaType formUrlEncodedContentType =
            MediaType.parse("application/x-www-form-urlencoded");

    public static final MediaType jsonContentType =
            MediaType.parse("application/json; charset=utf-8");

    public static final MediaType textPlainContentType =
            MediaType.parse("text/plain; charset=utf-8");

    private MediaType mMediaType;

    public ContentTypeInterceptor(MediaType mediaType) {
        mMediaType = mediaType;
        if (mediaType == null) {
            mMediaType = formUrlEncodedContentType;
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        /// GET 请求不需要设置Content-type
        if (request.method().equals("GET")) {
            return chain.proceed(request);
        }

        // 如果使用Retrofit注解@Headers("Content-type: ")
        // body为一个代理类(retrofit2.RequestBuilder$ContentTypeOverridingRequestBody)
        // 则使用代理中的Content-Type，优先级高于拦截器通用设置
        RequestBody body = request.body();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            MediaType mediaType = formBody.contentType();
            // 判断是否为FromBody默认的Content-Type, 默认application/x-www-form-urlencoded
            // 非默认值则表示客户端已更改为期望的Content-type，优先级大于拦截器通用设置，
            // 则不需要修改此次request的Content-Type
            if (mediaType != null && getContentType(mediaType)
                    .equals(getContentType(formUrlEncodedContentType))) {
                //
                if (!getContentType(mMediaType).equals(getContentType(mediaType))) {
                    Request.Builder newBuilder = request.newBuilder();
                    RequestBody newBody = new ContentTypeOverridingRequestBody(
                            body, mMediaType);

                    newBuilder.method(request.method(), newBody).build();
                    Request newRequest = newBuilder.build();
                    Response response = chain.proceed(newRequest);
                    return response;
                }
            }
        }

        return chain.proceed(request);
    }

    private String getContentType(MediaType mediaType) {
        if (mediaType != null) {
            return mediaType.toString();
        }
        return null;
    }

    private static class ContentTypeOverridingRequestBody extends RequestBody {
        private final RequestBody delegate;
        private final MediaType contentType;

        ContentTypeOverridingRequestBody(RequestBody delegate, MediaType contentType) {
            this.delegate = delegate;
            this.contentType = contentType;
        }

        @Override public MediaType contentType() {
            return contentType;
        }

        @Override public long contentLength() throws IOException {
            return delegate.contentLength();
        }

        @Override public void writeTo(BufferedSink sink) throws IOException {
            delegate.writeTo(sink);
        }
    }

}
