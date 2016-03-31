package com.b5m.core.dao.provider.base;

import com.b5m.core.dao.helper.MapperHelper;
import com.b5m.core.dao.provider.core.BaseProvider;
import com.b5m.core.dao.util.SqlUtils;
import org.apache.ibatis.mapping.MappedStatement;

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
public class BaseDeleteProvider extends BaseProvider {

    public BaseDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String deleteByPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.deleteFromTable(entityClass));
        sql.append(SqlUtils.wherePKColumns(entityClass));

        return sql.toString();
    }

    public String deleteByPrimaryKeys(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.deleteFromTable(entityClass));
        sql.append(SqlUtils.wherePKColumnsWithMultiValue(entityClass));

        return sql.toString();
    }

    public String deleteByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.deleteFromTable(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));

        return sql.toString();
    }

    public String deleteAll(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.deleteFromTable(entityClass));

        return sql.toString();
    }

}
