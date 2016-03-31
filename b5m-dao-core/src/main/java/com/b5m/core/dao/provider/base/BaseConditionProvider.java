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
public class BaseConditionProvider extends BaseProvider {

    public BaseConditionProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String deleteByCondition(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.deleteFromTable(entityClass));
        sql.append("<where>");
        sql.append("<foreach collection=\"list\" item=\"cnd\" index = \"index\">");
        sql.append(" AND ${cnd}");
        sql.append("</foreach>");
        sql.append("</where>");

        return sql.toString();
    }

//    public String updateByCondition(MappedStatement ms) {
//        return null;
//    }

    public String findOneByCondition(MappedStatement ms) {
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
        sql.append(" limit 1");

        return sql.toString();
    }

    public String countByCondition(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectCount(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append("<where>");
        sql.append("<foreach collection=\"list\" item=\"cnd\" index = \"index\">");
        sql.append(" AND ${cnd}");
        sql.append("</foreach>");
        sql.append("</where>");

        return sql.toString();
    }

    public String findAllByCondition(MappedStatement ms) {
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

        return sql.toString();
    }

    public String findAllByConditionAndSort(MappedStatement ms) {
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
        return sql.toString();
    }

}
