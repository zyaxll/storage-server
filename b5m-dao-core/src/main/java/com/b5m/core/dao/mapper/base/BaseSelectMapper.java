package com.b5m.core.dao.mapper.base;

import com.b5m.core.entity.Sort;
import com.b5m.core.dao.provider.base.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 公用查询Mapper, 通过{@link BaseSelectProvider}提供具体SQL生成
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
public interface BaseSelectMapper<T, ID extends Serializable> {

    @SelectProvider(type= BaseSelectProvider.class, method= "dynamicSQL")
    boolean exists(ID id);

    @SelectProvider(type= BaseSelectProvider.class, method= "dynamicSQL")
    boolean existsByEntity(T entity);


    @SelectProvider(type= BaseSelectProvider.class, method= "dynamicSQL")
    T findOne(ID id);

    @SelectProvider(type= BaseSelectProvider.class, method= "dynamicSQL")
    T findOneByEntity(T entity);


    @SelectProvider(type= BaseSelectProvider.class, method= "count")
    long count();

    @SelectProvider(type= BaseSelectProvider.class, method= "countByEntity")
    long countByEntity(T entity);


    @SelectProvider(type= BaseSelectProvider.class, method= "findAll")
    List<T> findAll();

    @SelectProvider(type= BaseSelectProvider.class, method= "findAllByPrimaryKeys")
    List<T> findAllByPrimaryKeys(Iterable<ID> iterable);

    @SelectProvider(type= BaseSelectProvider.class, method= "findAllBySort")
    List<T> findAllBySort(Sort sort);

    @SelectProvider(type= BaseSelectProvider.class, method= "findAllByEntity")
    List<T> findAllByEntity(T entity);

    @SelectProvider(type= BaseSelectProvider.class, method= "findAllByEntityAndSort")
    List<T> findAllByEntityAndSort(T entity, Sort sort);

}
