package com.b5m.core.dao.mapper.base;

import com.b5m.core.entity.Attribute;
import com.b5m.core.dao.provider.base.BaseUpdateProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;

/**
 * @description: 公用更新Mapper, 通过{@link BaseUpdateProvider}提供具体SQL生成
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
public interface BaseUpdateMapper<T, ID extends Serializable> {

    /**
     * 通过ID, 更新实体
     * @param id    ID
     * @param entity  实体
     */
    @UpdateProvider(type= BaseUpdateProvider.class, method= "updateByPrimaryKey")
    void update(ID id, T entity);

    /**
     * 通过实体对象中的属性进行查询, 并更新
     * @param entity    实体
     */
    @UpdateProvider(type= BaseUpdateProvider.class, method= "dynamicSQL")
    void updateByEntity(T entity);

    /**
     * 通过ID, 更新实体中的部分属性
     * @param entity 实体属性, 其中key=propertyName
     */
    @UpdateProvider(type = BaseUpdateProvider.class, method = "dynamicSQL")
    void updateByAttribute(@Param("id") ID id, @Param("entity") Attribute entity);

}
