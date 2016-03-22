/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.storage.util;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

/**
 * sql工具类
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version Nov 26, 2015 8:48:39 PM
 */
public class SqlUtils {

    public static final int MYSQL = 0;
    public static final int ORACLE = 1;
    public static final int DEFAULT_TYPE = MYSQL;

    private SqlUtils(){}

    /**
     * 将{@link PreparedStatement}的sql表达式转换成标准的sql,默认为MYSQL类型
     * 
     * @param sql, prepareSQL
     * @param params, prepareSQL中所包括的参数 数组下标与prepareSQL中的"?"顺序一致
     * @return
     */
    public static String realSql(String sql, Object... params) {
        return realSql(sql, DEFAULT_TYPE, params);
    }

    /**
     * 将{@link PreparedStatement}的sql表达式转换成标准的sql
     * 
     * @param sql prepareSQL
     * @param type 数据库类型，0为MYSQL，1为ORACLE
     * @param params prepareSQL中所包括的参数<br>
     *        数组下标与prepareSQL中的"?"顺序一致
     * @return
     */
    public static String realSql(String sql, int type, Object... params) {
        if (sql == null){
            return null;
        }
        if (params == null){
            return sql;
        }
        StringBuilder sb = new StringBuilder(sql);
        int index = 0;
        for (Object obj : params) {
            index = sb.indexOf("?", index);
            if (index < 0)
                return sb.toString();
            String value = getSqlTypeValue(obj, type);
            sb.replace(index, index + 1, value);
            index += value.length();
        }
        return sb.toString();
    }

    private static String getSqlTypeValue(Object obj, int type) {
        if (obj instanceof String) {
            return obj == null ? "''" : "'" + obj.toString() + "'";
        } else if (obj instanceof java.sql.Date) {
            return getDate((java.sql.Date) obj, type);
        } else if (obj instanceof Timestamp) {
            return getTimestamp((Timestamp) obj, type);
        } else {
            // ----数字类型
            return obj == null ? "" : obj.toString();
        }
    }

    private static final DateFormat stampFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");

    private static String getTimestamp(Timestamp time, int type) {
        switch (type) {
            case ORACLE:
                if (time == null)
                    return "SYSTIMESTMAP";
                return "TO_TIMESTAMP('" + stampFormat.format(time) + "','yyyymmdd hh24:mi:ssxff')";
            case MYSQL:
                if (time == null)
                    return "now()";
                return "'" + stampFormat.format(time) + "'";
            default:
                return getTimestamp(time, DEFAULT_TYPE);
        }
    }

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    private static String getDate(java.sql.Date date, int type) {

        switch (type) {
            case ORACLE:
                if (date == null)
                    return "SYSDATE";
                return "TO_DATE('" + dateFormat.format(date) + "','yyyymmdd hh24:mi:ss')";
            case MYSQL:
                if (date == null)
                    return "now()";
                return "'" + dateFormat.format(date) + "'";
            default:
                return getDate(date, DEFAULT_TYPE);
        }
    }

    public static String changeSQLExpression(Collection<String> data) {
        if (data == null || data.isEmpty())
            return "('')";
        StringBuilder sb = new StringBuilder("('");
        Iterator<String> itr = data.iterator();
        sb.append(itr.next());
        while (itr.hasNext()) {
            sb.append("','").append(itr.next());
        }
        sb.append("')");
        return sb.toString();
    }

    public static String changeSQLExpression(String[] data) {
        int len = data.length;
        if (data == null || len == 0)
            return "('')";
        StringBuilder sb = new StringBuilder("('");
        sb.append(data[0]);
        for (int i = 1; i < len; ++i) {
            sb.append("','").append(data[i]);
        }
        sb.append("')");
        return sb.toString();
    }
    
}
