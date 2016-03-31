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
public class BaseSelectProvider extends BaseProvider {

    public BaseSelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String exists(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectCount(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.wherePKColumns(entityClass));

        return sql.toString();
    }

    public String existsByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectCount(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));
        sql.append(" limit 1 ");

        return sql.toString();
    }

    public String findOne(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.wherePKColumns(entityClass));

        return sql.toString();
    }

    public String findOneByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));
        sql.append(SqlUtils.singleLimit());

        return sql.toString();
    }

    public String count(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectCount(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));

        return sql.toString();
    }

    public String countByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectCount(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));

        return sql.toString();
    }

    public String findAll(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.orderByDefault(entityClass));

        return sql.toString();
    }

    public String findAllByPrimaryKeys(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.wherePKColumnsWithMultiValue(entityClass));

        return sql.toString();
    }

    public String findAllBySort(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.orderBy(entityClass));

        return sql.toString();
    }

    public String findAllByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));
        sql.append(SqlUtils.orderByDefault(entityClass));

        return sql.toString();
    }

    public String findAllByEntityAndSort(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass));
        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));
        sql.append(SqlUtils.orderBy(entityClass));

        return sql.toString();
    }

}
