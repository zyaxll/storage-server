package com.b5m.storage.service.core.impl;

import com.b5m.storage.dao.core.CommonMapper;
import com.b5m.storage.service.core.ICacheService;
import com.b5m.storage.service.core.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-22
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-22       Leo.li          1.0             TODO
 */
public class AbstractCommonService<T> implements ICommonService<T> {

    @Autowired
    private ICacheService cacheService;

    private CommonMapper<T> mapper;

    public CommonMapper<T> getMapper() {
        return mapper;
    }

    public void setMapper(CommonMapper<T> mapper) {
        this.mapper = mapper;
    }
}
