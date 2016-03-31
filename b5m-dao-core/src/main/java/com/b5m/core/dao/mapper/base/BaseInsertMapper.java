package com.b5m.core.dao.mapper.base;

import com.b5m.core.dao.provider.base.BaseInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.io.Serializable;

/**
 * @description: 公用插入Mapper, 通过{@link BaseInsertProvider}提供具体SQL生成
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
public interface BaseInsertMapper<T, ID extends Serializable> {

    /**
     * 插入单个实体, 并把ID反写到实体对象中
     * @param entity    需要插入的实体
     * @return 插入条数
     */
    @Options(useGeneratedKeys = true)
    @InsertProvider(type = BaseInsertProvider.class, method = "save")
    int save(T entity);

    /**
     * 批量插入多个实体
     * @param iterable    实体列表
     * @return 插入条数
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "saveInBatch")
    int saveInBatch(Iterable<T> iterable);

}
