package com.sinieco.common.network;

public class NetworkConfig {

    /**
     * 接口参数key
     */
    public static final String REQUEST_KEY = "requestGson";

    /**
     * 分页默认条目个数
     */
    public static final int PAGE_ITEM_COUNT = 20;

    /**
     * 接口服务器地址
     */
    public String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private static class SingleTon {
        private static final NetworkConfig INSTANCE = new NetworkConfig();
    }

    public static NetworkConfig getInstance() {
        return SingleTon.INSTANCE;
    }

}
