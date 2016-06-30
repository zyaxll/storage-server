package com.b5m.core.dao.mapper.base;

import com.b5m.core.entity.Condition;
import com.b5m.core.entity.Sort;
import com.b5m.core.dao.provider.base.BasePageProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 公用分页Mapper, 通过{@link BasePageProvider}提供具体SQL生成
 *
 * <p>当Provider中的method与Mapper中的方法相同时, 可以使用dynamicSQL替代</p>
 *
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-29
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-29       Leo.li          1.0             TODO
 */
public interface BasePageMapper<T, ID extends Serializable> {

    @SelectProvider(type= BasePageProvider.class, method= "dynamicSQL")
    List<T> findPageByEntity(@Param("entity") T entity, @Param("from") long from, @Param("size") long size);

    @SelectProvider(type= BasePageProvider.class, method= "dynamicSQL")
    List<T> findPageByEntityAndSort(@Param("entity") T entity, @Param("sort") Sort sort, @Param("from") long from, @Param("size") long size);

    @SelectProvider(type= BasePageProvider.class, method= "dynamicSQL")
    List<T> findPageByCondition(@Param("list") Iterable<Condition> iterator, @Param("from") long from, @Param("size") long size);

    @SelectProvider(type= BasePageProvider.class, method= "dynamicSQL")
    List<T> findPageByConditionAndSort(@Param("list") Iterable<Condition> iterator, @Param("sort") Sort sort, @Param("from") long from, @Param("size") long size);

}
