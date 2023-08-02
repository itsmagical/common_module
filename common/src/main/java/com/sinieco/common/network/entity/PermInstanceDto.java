package com.sinieco.common.network.entity;

/**
 * 权限
 * Created by LiuHe on 2018/9/19.
 */

public class PermInstanceDto {

    /**
     *  权限id
     */
    private Long dataId;

    /**
     *  权限图标
     */
    private String icon;

    /**
     * 权限等级
     */
    private Integer menuLevel;

    /**
     *  权限文本 用于显示
     */
    private String menuText;

    /**
     *  权限名称
     */
    private String name;

    /**
     * 权限父id
     */
    private Long parentId;

    /**
     * 序列号
     */
    private Integer serialno;

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSerialno() {
        return serialno;
    }

    public void setSerialno(Integer serialno) {
        this.serialno = serialno;
    }
}
