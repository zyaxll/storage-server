package com.b5m.core.dao.provider.base;

import com.b5m.core.entity.EntityColumn;
import com.b5m.core.dao.helper.EntityHelper;
import com.b5m.core.dao.helper.MapperHelper;
import com.b5m.core.dao.provider.core.BaseProvider;
import com.b5m.core.dao.util.SqlUtils;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Set;

/**
 * @description: 公用插入SQL生成类
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
public class BaseInsertProvider extends BaseProvider {

    public BaseInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String save(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.insertIntoTable(entityClass));
        sql.append(SqlUtils.insertColumns(entityClass, true, false, false));
        sql.append(SqlUtils.insertValuesColumns(entityClass, true, false, false));

        return sql.toString();
    }

    public String saveInBatch(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.insertIntoTable(entityClass));
        sql.append(SqlUtils.insertColumns(entityClass, true, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");

        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }

}
