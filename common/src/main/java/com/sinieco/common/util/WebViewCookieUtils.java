package com.sinieco.common.util;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * WebView同步cookie Util
 * Created by LiuHe on 2018/10/24.
 */

public class WebViewCookieUtils {

    /**
     *  同步WebView Cookie
     */
    public static void syncWebViewCookie(Context context, String url, String cookie) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            throw new NullPointerException("HttpUrl is null");
        }
        setCookie(context, httpUrl.host(), cookie);
    }

    /**
     *  同步WebView Cookie
     */
    public static void syncWebViewCookie(Context context, Cookie cookie) {
        if (cookie == null) {
            throw new NullPointerException("cookie is null");
        }
        setCookie(context, cookie.domain(), cookie.toString());
    }

    private static void setCookie(Context context, String domain, String cookie) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(domain, cookie);
        sync();
    }

    private static void sync() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    /**
     *  移除WebView Cookie
     */
    public static void removeWebViewCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        sync();
    }

}
