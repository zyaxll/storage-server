package com.b5m.core.dao.provider.base;

import com.b5m.core.dao.helper.MapperHelper;
import com.b5m.core.dao.provider.core.BaseProvider;
import com.b5m.core.dao.util.SqlUtils;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * @description: 公用更新SQL生成类
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
public class BaseUpdateProvider extends BaseProvider {

    public BaseUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateByPrimaryKey(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.updateTable(entityClass));
        sql.append(SqlUtils.updateSetColumns(entityClass, null, false, false));
        sql.append(SqlUtils.wherePKColumns(entityClass));

        return sql.toString();
    }

    public String updateByEntity(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.updateTable(entityClass));
        sql.append(SqlUtils.updateSetColumns(entityClass, null, false, false));
//        sql.append(SqlUtils.whereAllIfColumns(entityClass, true));
        sql.append(SqlUtils.wherePKColumns(entityClass));

        return sql.toString();
    }

    public String updateByAttribute(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.updateTable(entityClass));

        sql.append("<set>");
        sql.append("<foreach collection=\"entity.attrs\" item=\"p\" index =\"index\" separator=\",\">");
        sql.append("${p.name} = #{p.value}");
        sql.append("</foreach>");
        sql.append("</set>");

        sql.append(" WHERE id = #{id} ");

        return sql.toString();
    }

}
