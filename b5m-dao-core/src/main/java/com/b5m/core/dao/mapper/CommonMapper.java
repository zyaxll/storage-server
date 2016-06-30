package com.b5m.core.dao.mapper;

import com.b5m.core.dao.mapper.base.BaseConditionMapper;
import com.b5m.core.dao.mapper.base.BaseDeleteMapper;
import com.b5m.core.dao.mapper.base.BaseInsertMapper;
import com.b5m.core.dao.mapper.base.BasePageMapper;
import com.b5m.core.dao.mapper.base.BaseSelectMapper;
import com.b5m.core.dao.mapper.base.BaseUpdateMapper;

import java.io.Serializable;

/**
 * @description: 公用Mapper, 所有Mapper需继承该Mapper
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-28
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-28       Leo.li          1.0             TODO
 */
public interface CommonMapper<T, ID extends Serializable>
        extends BaseInsertMapper<T, ID>,
        BaseUpdateMapper<T, ID>,
        BaseDeleteMapper<T, ID>,
        BaseSelectMapper<T, ID>,
        BaseConditionMapper<T, ID>,
        BasePageMapper<T, ID>,
        Marker {

}
