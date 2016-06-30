/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.core.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 带数据的分页工具类
 * @author lucky.liu
 * @version v1.3.1 2014年8月7日 下午3:45:40
 * @param <T> 数据类型
 */
@XmlRootElement(name = "PageList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PageImpl<T> implements Page<T> {

    private static final long serialVersionUID = -4168124165860775460L;
    
    private List<T> content;
    
    @XmlTransient
    private Pager pager;
    
    public PageImpl() {
        pager = new Pager();
        content = new ArrayList<T>();
    }

    public PageImpl(long totalRecordCount) {
        this();
        pager.setTotalRecordCount(totalRecordCount);
    }

    public PageImpl(int pageNo, long totalRecordCount) {
        this(totalRecordCount);
        pager.setPageNo(pageNo);
    }

    public PageImpl(int pageNo, long totalRecordCount, int pageSize) {
        this();
        pager.setPageSize(pageSize);
        pager.setTotalRecordCount(totalRecordCount);
        pager.setPageNo(pageNo);
    }
    
    public PageImpl(Pager pager){
        this();
        this.pager = pager;
    }
    
    public PageImpl(Pager pager, List<T> content){
        this(pager);
        this.content = content;
    }

    @XmlElement
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setPageNo(int pageNo) {
        pager.setPageNo(pageNo);
    }

    @XmlElement
    public int getPageNo() {
        return pager.getPageNo();
    }

    public void setPageSize(int pageSize) {
        pager.setPageSize(pageSize);
    }

    @XmlElement
    public int getPageSize() {
        return pager.getPageSize();
    }

    public void setTotalRecordCount(long totalRecordCount) {
        pager.setTotalRecordCount(totalRecordCount);
    }

    @XmlElement
    public long getTotalRecordCount() {
        return pager.getTotalRecordCount();
    }

    @XmlElement
    public int getPageCount() {
        return pager.getPageCount();
    }

    @XmlElement
    public boolean isFirstPage() {
        return pager.isFirstPage();
    }

    @XmlElement
    public boolean isLastPage() {
        return pager.isLastPage();
    }

    @XmlElement
    public boolean hasNextPage() {
        return pager.hasNextPage();
    }

    @XmlElement
    public boolean hasPreviousPage() {
        return pager.hasPreviousPage();
    }

    @XmlElement
    public int getCurrentPageIndex() {
        return pager.getCurrentPageIndex();
    }

    @XmlElement
    public int getCurrentPageRecordCount() {
        return pager.getCurrentPageRecordCount();
    }

    @XmlElement
    public long getFirstIndexOfCurrentPage() {
        return pager.getFirstIndexOfCurrentPage();
    }

    @XmlElement
    public long getLastIndexOfCurrentPage() {
        return pager.getLastIndexOfCurrentPage();
    }

    @XmlElement
    public long getFirstIndexOfPreviousPage() {
        return pager.getFirstIndexOfPreviousPage();
    }

    @XmlElement
    public long getFirstIndexOfNextPage() {
        return pager.getFirstIndexOfNextPage();
    }

    @XmlElement
    public long getFirstIndexOfPage(int pageNo) {
        return pager.getFirstIndexOfPage(pageNo);
    }

    @XmlElement
    public long getLastIndexOfPage(int pageNo) {
        return pager.getLastIndexOfPage(pageNo);
    }

    public void setPager(Pager pager){
        this.pager = pager;
    }
}
