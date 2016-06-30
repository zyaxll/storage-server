package com.b5m.core.dao.mapper.base;

import com.b5m.core.dao.provider.base.BaseDeleteProvider;
import org.apache.ibatis.annotations.DeleteProvider;

import java.io.Serializable;

/**
 * @description: 公用删除Mapper, 通过{@link BaseDeleteProvider}提供具体SQL生成
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
public interface BaseDeleteMapper<T, ID extends Serializable> {

    @DeleteProvider(type= BaseDeleteProvider.class, method= "dynamicSQL")
    void deleteByPrimaryKey(ID id);

    @DeleteProvider(type= BaseDeleteProvider.class, method= "dynamicSQL")
    void deleteByPrimaryKeys(Iterable<ID> ids);

    @DeleteProvider(type= BaseDeleteProvider.class, method= "dynamicSQL")
    void deleteByEntity(T entity);

//    @DeleteProvider(type= BaseDeleteProvider.class, method= "deleteAll")
//    void deleteAll();

}
