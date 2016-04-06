package com.b5m.core.dao.spring;

import com.b5m.core.dao.helper.MapperHelper;

/**
 * @description: 自定义MapperFactoryBean, 增加{@link MapperHelper}, 是配置在启动时可以自动注册初始化
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-4-1
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-4-1       Leo.li          1.0             TODO
 */
public class MapperFactoryBean<T> extends org.mybatis.spring.mapper.MapperFactoryBean<T> {

    private MapperHelper mapperHelper;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }


    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        //通用Mapper
        if (mapperHelper.isExtendCommonMapper(getObjectType())) {
            mapperHelper.processConfiguration(getSqlSession().getConfiguration(), getObjectType());
        }
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }
}