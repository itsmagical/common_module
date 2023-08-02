package com.sinieco.common.network.entity;

public class User {

    private User user;

    private String avatarId;
    private Long userId;
    private String name;
    private String loginName;
    private String password;
    private int orgId;
    private String orgName;
    private String companyId;
    private String companyName;
    Boolean accExpired;
    Boolean passExpired;
    Boolean accLocked;
    Boolean accDeleted;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getAccExpired() {
        return accExpired;
    }

    public void setAccExpired(Boolean accExpired) {
        this.accExpired = accExpired;
    }

    public Boolean getPassExpired() {
        return passExpired;
    }

    public void setPassExpired(Boolean passExpired) {
        this.passExpired = passExpired;
    }

    public Boolean getAccLocked() {
        return accLocked;
    }

    public void setAccLocked(Boolean accLocked) {
        this.accLocked = accLocked;
    }

    public Boolean getAccDeleted() {
        return accDeleted;
    }

    public void setAccDeleted(Boolean accDeleted) {
        this.accDeleted = accDeleted;
    }
}
