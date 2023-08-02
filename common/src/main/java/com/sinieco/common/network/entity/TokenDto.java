package com.sinieco.common.network.entity;

/**
 *  Token过期 刷新token后响应的数据model
 */
public class TokenDto {

    /**
     *  token
     */
    private String access_token;

    /**
     *  类型
     */
    private String token_type;

    /**
     *  刷新token参数
     */
    private String refresh_token;

    private Integer expires_in;

    /**
     *  过期时间
     */
    private String expiredTime;

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

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "TokenDto{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", expiredTime='" + expiredTime + '\'' +
                '}';
    }
}
