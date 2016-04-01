package com.b5m.core.dao.provider.base;

import com.b5m.core.dao.helper.MapperHelper;
import com.b5m.core.dao.provider.core.BaseProvider;
import com.b5m.core.dao.util.SqlUtils;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @description: 公用分页SQL生成类
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
public class BasePageProvider extends BaseProvider {

    public BasePageProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String findPageByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, "entity", true));
        sql.append(SqlUtils.orderByDefault(entityClass));
        sql.append("limit ${from}, ${size}");

        return sql.toString();
    }

    public String findPageByEntityAndSort(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.orderBy(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));
        sql.append(" ORDER BY ${sort} ");
        sql.append(" limit ${from}, ${size}");

        return sql.toString();
    }

    public String findPageByCondition(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append("<where>");
        sql.append("<foreach collection=\"list\" item=\"cnd\" index = \"index\">");
        sql.append(" AND ${cnd}");
        sql.append("</foreach>");
        sql.append("</where>");
        sql.append(SqlUtils.orderByDefault(entityClass));
        sql.append(" limit ${from}, ${size}");

        return sql.toString();
    }

    public String findPageByConditionAndSort(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append("<where>");
        sql.append("<foreach collection=\"list\" item=\"cnd\" index = \"index\">");
        sql.append(" AND ${cnd}");
        sql.append("</foreach>");
        sql.append("</where>");
        sql.append(" ORDER BY ${sort}");
        sql.append(" limit ${from}, ${size}");

        return sql.toString();
    }

}
