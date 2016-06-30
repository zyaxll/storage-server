package com.b5m.core.dao.util;

import com.b5m.core.dao.helper.EntityHelper;
import com.b5m.core.entity.EntityColumn;
import com.b5m.core.entity.EntityTable;
import com.b5m.utils.Assert;

/**
 * @description: 属性工具类, 后续考虑实现通过实体属性名做为参数
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-4-5
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-4-5       Leo.li          1.0             TODO
 */
public class PropertyUtils {

    public static <T> String getKey(Class<T> clazz, String key) {
        Assert.notNull(clazz, String.format("class [%s] is null", clazz));
        Assert.notNull(key, String.format("key [%s] is null", key));

        EntityTable table = EntityHelper.getEntityTable(clazz);
        Assert.notNull(table, String.format("class %s not exist", clazz));

        EntityColumn column = table.getKeyColumnMap().get(key);
        if (null == column) {
            column = table.getKeyPropertyMap().get(key);
            Assert.notNull(column, String.format("table %s not exist column %s", table.getName(), key));
        }

        return column.getColumn();
    }

}
