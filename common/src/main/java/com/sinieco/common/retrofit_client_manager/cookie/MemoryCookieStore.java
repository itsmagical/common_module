package com.sinieco.common.retrofit_client_manager.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 *
 * Created by LiuHe on 2018/9/10.
 */

public class MemoryCookieStore implements CooKieStore {

    private final HashMap<String, List<Cookie>> allCoolies = new HashMap<>();

    @Override
    public void add(HttpUrl url, List<Cookie> cookies) {
        List<Cookie> oldCookies = allCoolies.get(url.host());
        ArrayList<Cookie> needRemove = new ArrayList<>();
        for (Cookie newCookie : cookies) {
            for (Cookie oldCookie : oldCookies) {
                if (newCookie.name().equals(oldCookie.name())) {
                    needRemove.add(oldCookie);
                }
            }
        }
        oldCookies.removeAll(needRemove);
        oldCookies.addAll(cookies);
//        allCoolies.put(url.host(), cookies);
    }

    @Override
    public List<Cookie> get(HttpUrl uri) {
        List<Cookie> cookies = allCoolies.get(uri.host());
        if (cookies == null) {
            cookies = new ArrayList<>();
            allCoolies.put(uri.host(), cookies);
        }
        return cookies;
    }

    @Override
    public List<Cookie> getCookies() {
        ArrayList<Cookie> cookies = new ArrayList<>();
        Set<String> httpUrls = allCoolies.keySet();
        for (String httpUrl : httpUrls) {
            cookies.addAll(allCoolies.get(httpUrl));
        }
        return cookies;
    }

    @Override
    public boolean remove(HttpUrl uri, Cookie cookie) {
        List<Cookie> cookies = allCoolies.get(uri);
        if (cookie != null) {
            return cookies.remove(cookie);
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        allCoolies.clear();
        return true;
    }
}
