package com.b5m.core.dao.mapper.base;

import com.b5m.core.entity.Condition;
import com.b5m.core.entity.Sort;
import com.b5m.core.dao.provider.base.BaseConditionProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;

/**
 * @description: TODO
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
public interface BaseConditionMapper<T, ID extends Serializable> {

    @DeleteProvider(type= BaseConditionProvider.class, method= "deleteByCondition")
    void deleteByCondition(Iterable<Condition> iterator);

//    @DeleteProvider(type= BaseConditionProvider.class, method= "updateByCondition")
//    void updateByCondition(Iterable<Condition> iterator);

    @SelectProvider(type= BaseConditionProvider.class, method= "findOneByCondition")
    T findOneByCondition(Iterable<Condition> iterator);

    @SelectProvider(type= BaseConditionProvider.class, method= "countByCondition")
    long countByCondition(Iterable<Condition> iterator);

    @SelectProvider(type= BaseConditionProvider.class, method= "findAllByCondition")
    List<T> findAllByCondition(Iterable<Condition> iterator);

    @SelectProvider(type= BaseConditionProvider.class, method= "findAllByConditionAndSort")
    List<T> findAllByConditionAndSort(@Param("list") Iterable<Condition> iterator, @Param("sort") Sort sort);

}
