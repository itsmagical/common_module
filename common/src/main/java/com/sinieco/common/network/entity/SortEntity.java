package com.sinieco.common.network.entity;

/**
 * 排序对象
 * @author xuejiangtao
 * 2015年4月29日 下午9:14:50
 */
public class SortEntity {
    
    private String sortProperty;
    
    private boolean asc;
    
    public SortEntity(String sortProperty, boolean asc) {
        this.sortProperty = sortProperty;
        this.asc = asc;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
