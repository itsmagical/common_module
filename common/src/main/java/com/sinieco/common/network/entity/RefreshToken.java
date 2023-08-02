package com.sinieco.common.network.entity;

/**
 *  refreshToken
 *  token过期后需要refreshToken获取新的token
 */
public class RefreshToken {

    /**
     *  refreshToken过期时间，refreshToken过期后需重新登录
     *  refreshToken手机端暂时定为不过期，暂时不需要处理过期重新登录
     */
    private String expiration;

    /**
     *  refreshToken
     */
    private String value;

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
