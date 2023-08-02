package com.sinieco.common.network.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息对象。默认情况下认为需要分页并排序。
 * 如果不需要分页，需要调用setNeedsPaginate()方法。
 * 如果不需要排序，需要调用setNeedsSort()方法。
 * @author xuejiangtao
 * 2015年2月27日 下午4:33:00
 */
public class Pagination {
    
    /**
     * 标记是否分页。
     */
    public Boolean needsPaginate;
    
    /**
     * 标记是否排序。
     */
    public Boolean needsSort;

    /**
     * 分页的起始记录序号。
     */
    public int startPos;
    
    /**
     * 每页需要取出的记录大小。
     */
    public int amount;

    public Boolean dir;

    public String sort;
    
    public List<SortEntity> sorts;
    
    /**
     * 默认构造函数，默认不分页不排序。
     */
    public Pagination() {
        this.needsPaginate = false;
        this.needsSort = false;
    }

    public Pagination(int startPos, int amount) {
        this.startPos = startPos;
        this.amount = amount;
        this.needsPaginate = true;
    }

    public Pagination(int startPos, int amount, String sort, boolean dir) {
        this.startPos = startPos;
        this.amount = amount;
        SortEntity entry = new SortEntity(sort, dir);
        sorts = new ArrayList<>();
        sorts.add(entry);
        needsSort = true;
        needsPaginate = true;
    }

    /**
     * 不分页只排序
     * 必须指定排序的属性以及是升序还是降序
     * 构造函数
     * @param sorts
     */
    public Pagination(List<SortEntity> sorts) {
        this.needsPaginate = false;
        this.needsSort = true;
        this.sorts = sorts;
    }
    
    /**
     * 可以分别指定是否需要分页和需要排序的对象。
     * 如果指定了需要分页，那么调用者必须保证为其添加分页信息，否则默认从第一条记录开始，每页30条记录。
     * 如果指定了需要排序，那么调用者必须保证为其添加排序信息，否则默认以id属性顺序排列。
     * @param needsPaginate 是否需要分页。
     * @param needsSort 是否需要排序。
     */
    /*public Pagination(boolean needsPaginate, boolean needsSort) {
        this.needsPaginate = needsPaginate;
        this.needsSort = needsSort;
        this.startPos = 0;
        this.amount = 30;
        SortEntity sort = new SortEntity();
        sort.setSortProperty("id");
        sort.setAsc(true);
        ArrayList<SortEntity> sorts = new ArrayList<SortEntity>();
        sorts.add(sort);
        this.sorts = sorts;
    }*/

    /**
     * 即分页也排序，并给出相应的参数。
     * @param startPos 分页的起始记录编号。
     * @param amount 每页大小。
     */
    public Pagination(int startPos, int amount, List<SortEntity> sorts) {
        this.needsPaginate = true;
        this.needsSort = true;
        this.startPos = startPos;
        this.amount = amount;
        this.sorts = sorts;
    }

}
