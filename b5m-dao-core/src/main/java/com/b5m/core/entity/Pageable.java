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
    
    boolean isFirstPage();

    boolean isLastPage();

    boolean hasPreviousPage();

    boolean hasNextPage();

    int getCurrentPageIndex();

    int getPageNo();

    int getPageCount();

    int getPageSize();

    int getCurrentPageRecordCount();

    long getTotalRecordCount();

    long getFirstIndexOfCurrentPage();

    long getLastIndexOfCurrentPage();

    long getFirstIndexOfPreviousPage();

    long getFirstIndexOfNextPage();

    long getFirstIndexOfPage(int pageNo);

    long getLastIndexOfPage(int pageNo);

}