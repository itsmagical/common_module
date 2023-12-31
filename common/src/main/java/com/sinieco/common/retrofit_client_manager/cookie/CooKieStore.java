package com.sinieco.common.retrofit_client_manager.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 *
 * Created by LiuHe on 2018/9/10.
 */

public interface CooKieStore {
     void add(HttpUrl url, List<Cookie> cookies);
     List<Cookie> get(HttpUrl uri);

     List<Cookie> getCookies();

     boolean remove(HttpUrl uri, Cookie cookie);

     boolean removeAll();
}
