package com.b5m.core.dao.mapper.base;

import com.b5m.core.dao.provider.base.BaseDeleteProvider;
import org.apache.ibatis.annotations.DeleteProvider;

import java.io.Serializable;

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
public interface BaseDeleteMapper<T, ID extends Serializable> {

    @DeleteProvider(type= BaseDeleteProvider.class, method= "deleteByPrimaryKey")
    void deleteByPrimaryKey(ID id);

    @DeleteProvider(type= BaseDeleteProvider.class, method= "deleteByPrimaryKeys")
    void deleteByPrimaryKeys(Iterable<ID> ids);

    @DeleteProvider(type= BaseDeleteProvider.class, method= "deleteByEntity")
    void deleteByEntity(T entity);

//    @DeleteProvider(type= BaseDeleteProvider.class, method= "deleteAll")
//    void deleteAll();

}
