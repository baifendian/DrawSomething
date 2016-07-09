// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PageMessage.java

package com.bfd.util;

import java.util.HashMap;
import java.util.Map;

public class PageMessage {

    /** 当前页*/
    private int    currentPage;
    /** 上一页*/
    private int    previousPage;
    /** 下一页*/
    private int    nextPage;
    /** 尾页*/
    private int    endPage;
    /** 每页大小*/
    private int    pageSize;
    /** 排序类型*/
    private String sort;
    /** 排序字段*/
    private String order;
    /** 分页字段*/
    private Object pageField;

    public PageMessage(int currentPage, int count, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        endPage = count % pageSize != 0 ? count / pageSize + 1 : count / pageSize;
        if (endPage == 0)
            endPage = 1;
        if (currentPage > endPage)
            this.currentPage = endPage;
        previousPage = currentPage != 1 ? currentPage - 1 : 1;
        nextPage = currentPage != endPage ? currentPage + 1 : endPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Object getPageField() {
        return pageField;
    }

    public void setPageField(Object pageField) {
        this.pageField = pageField;
    }

    public static Map<String, Object> getPageMap(PageMessage pageMessage) {
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("startIndex",
                Integer.valueOf((pageMessage.getCurrentPage() - 1) * pageMessage.getPageSize()));
        maps.put("pageSize", pageMessage.getPageSize());
        if (pageMessage.getOrder() != null) {
            maps.put("order", pageMessage.getOrder());
        }
        if (pageMessage.getSort() != null) {
            maps.put("sort", pageMessage.getSort());
        }
        if (pageMessage.getPageField() != null) {
            maps.put("pageField", pageMessage.getPageField());
        }

        return maps;
    }

    public enum PageMessageSortEnum {
        ASC,
        DESC;
    }

}
