package com.sinieco.common.network.entity;

public class Authentication {

    /**
     *  token
     */
    private String access_token;

    /**
     *  类型
     */
    private String token_type;

    /**
     *  token过期时间
     */
    private String expiredTime;

    private RefreshToken refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public RefreshToken getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(RefreshToken refresh_token) {
        this.refresh_token = refresh_token;
    }
}
