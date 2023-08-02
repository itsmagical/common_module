package com.sinieco.common.retrofit_client_manager.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 *
 * Created by LiuHe on 2018/9/10.
 */

public class CookieJarImpl implements CookieJar, HasCookieStore {
    private CooKieStore cooKieStore;

    public CookieJarImpl(CooKieStore cooKieStore) {

        if (cooKieStore != null) {
            this.cooKieStore = cooKieStore;
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        List<Cookie> storeCookies = cooKieStore.getCookies();
        if (storeCookies.size() == 0) {
            cooKieStore.add(url, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cooKieStore.get(url);
    }

    @Override
    public CooKieStore getCookieStore() {
        return cooKieStore;
    }


}
