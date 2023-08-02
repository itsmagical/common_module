package com.sinieco.common.network.entity;

/**
 *  字典
 * Created by LiuHe on 2018/10/27.
 */

public class ADictionaryDto {

    private Long userId;

    /**
     *  字典值tableName
     */
    private String tableName;

    /**
     * 字典值attribute
     */
    private String attribute;

    /**
     * 此条字典值的value
     */
    private Integer value;

    /**
     *  字典值描述
     */
    private String description;

    public boolean selected;

    public ADictionaryDto() {}

    public ADictionaryDto(String tableName, String attribute) {
        this.tableName = tableName;
        this.attribute = attribute;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
