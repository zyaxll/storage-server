/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.core.entity;

import java.io.Serializable;

/**
 * @description 分页接口
 * @author lucky.liu
 * @version v1.3.1 2014年8月7日 下午3:28:46
 */
public interface Pageable extends Serializable {
    
    public boolean isFirstPage();

    public boolean isLastPage();

    public boolean hasPreviousPage();

    public boolean hasNextPage();

    public int getCurrentPageIndex();

    public int getPageNo();

    public int getPageCount();

    public int getPageSize();

    public int getCurrentPageRecordCount();

    public long getTotalRecordCount();

    public long getFirstIndexOfCurrentPage();

    public long getLastIndexOfCurrentPage();

    public long getFirstIndexOfPreviousPage();

    public long getFirstIndexOfNextPage();

    public long getFirstIndexOfPage(int pageNo);

    public long getLastIndexOfPage(int pageNo);
}