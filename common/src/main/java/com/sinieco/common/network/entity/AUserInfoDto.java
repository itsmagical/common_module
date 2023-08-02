package com.sinieco.common.network.entity;

import java.util.List;

/**
 * 用户信息
 * Created by LiuHe on 2018/9/10.
 */

public class AUserInfoDto {

    /**
     *  用户id
     */
    private long userId;

    /**
     *  用户名
     */
    private String userName;

    /**
     *  登录名
     */
    private String loginName;

    /**
     *  监管类型 1 焚烧、2 餐厨;
     */
    private int superviseType;

    /**
     *  密码
     */
    private String password;

    /**
     *  验证码
     */
    private String captcha;

    /**
     *  新密码
     */
    private String newPassword;

    /**
     *  确认密码
     */
    private String confirmPassword;


    /**
     *  登录类型
     *  1：用户名密码；
     *  2：手机号验证码
     *  3：修改密码
     *  4：绑定手机号
     *  5：修改基本信息
     */
    private Integer type;

    /**
     *  1:PC
     *  2:Android
     *  3:iOS
     */
    private Integer versionType;

    /**
     * 　手机号
     */
    private String mobile;

    /**
     *  头像
     */
    private String photoPath;

    /**
     *  性别
     */
    private String gender;

    /**
     *  邮箱
     */
    private String postBox;

    /**
     *  角色
     */
    private String userRole;

    /**
     *  用户组织id
     */
    private Long userOrgId;

    /**
     *  顶级组织描述
     */
    private String userOrgParStr;

    /**
     * 顶级组织 ，（顶级部门）
     */
    private Long userOrgPerId;

    /**
     *  部门id
     */
    private Long userOrgParId;

    /**
     *  岗位
     */
    private String positions;

    private Integer stage;

    private List<PermInstanceDto> permInstanceDtos;


    public AUserInfoDto() {};

    // 登录使用构造
    public AUserInfoDto(String loginName, String password, int type) {
        this.loginName = loginName;
        this.password = password;
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getSuperviseType() {
        return superviseType;
    }

    public void setSuperviseType(int superviseType) {
        this.superviseType = superviseType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVersionType() {
        return versionType;
    }

    public void setVersionType(Integer versionType) {
        this.versionType = versionType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPostBox() {
        return postBox;
    }

    public void setPostBox(String postBox) {
        this.postBox = postBox;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserOrgParStr() {
        return userOrgParStr;
    }

    public void setUserOrgParStr(String userOrgParStr) {
        this.userOrgParStr = userOrgParStr;
    }

    public Long getUserOrgId() {
        return userOrgId;
    }

    public void setUserOrgId(Long userOrgId) {
        this.userOrgId = userOrgId;
    }

    public Long getUserOrgPerId() {
        return userOrgPerId;
    }

    public void setUserOrgPerId(Long userOrgPerId) {
        this.userOrgPerId = userOrgPerId;
    }

    public Long getUserOrgParId() {
        return userOrgParId;
    }

    public void setUserOrgParId(Long userOrgParId) {
        this.userOrgParId = userOrgParId;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public List<PermInstanceDto> getPermInstanceDtos() {
        return permInstanceDtos;
    }

    public void setPermInstanceDtos(List<PermInstanceDto> permInstanceDtos) {
        this.permInstanceDtos = permInstanceDtos;
    }

    @Override
    public String toString() {
        return "AUserInfoDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", captcha='" + captcha + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", cnfirmPassword='" + confirmPassword + '\'' +
                ", type=" + type +
                ", mobile='" + mobile + '\'' +
                ", userRole='" + userRole + '\'' +
                ", permInstanceDtos=" + permInstanceDtos +
                '}';
    }
}
