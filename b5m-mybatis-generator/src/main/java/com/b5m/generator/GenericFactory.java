package com.b5m.generator;

import com.b5m.generator.bean.Column;
import com.b5m.generator.bean.Table;
import com.b5m.generator.core.GenericBase;
import com.b5m.generator.core.GenericType;
import com.b5m.generator.utils.ConnUtils;
import com.b5m.generator.utils.Constant;
import com.b5m.generator.utils.PropertyUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class GenericFactory {

    private static List<GenericBase> lstGeneric = new ArrayList<GenericBase>();

    public static void generic(List<String> lstTableName) {
        if (null == lstTableName) return;

        for (String tableName : lstTableName) {
            generic(tableName, Constant.TABLE_SCHEMA);
        }
    }

    public static void generic(String tableName) {
        generic(tableName, Constant.TABLE_SCHEMA);
    }

    public static void generic(String tableName, String tableSchema) {
        try {
            Table table = findTableByName(tableName, tableSchema);

            addGeneric(genericBean(GenericType.ENTITY, table));
            addGeneric(genericBean(GenericType.DAO, table));
            addGeneric(genericBean(GenericType.SERVICE, table));
            addGeneric(genericBean(GenericType.SERVICE_IMPL, table));

            if (null != table) {
                genericJava();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void genericJava() {
        try {
            for (GenericBase generic : lstGeneric) {
                generic.generic();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GenericBase genericBean(GenericType type, Table table) {
        GenericBase base;
        switch (type) {
            case ENTITY:
                base = new GenericEntity(table);
                break;
            case DAO:
                base = new GenericDao(table);
                break;
            case SERVICE:
                base = new GenericService(table);
                break;
            case SERVICE_IMPL:
                base = new GenericServiceImpl(table);
                break;
            default:
                base = null;
        }

        return base;
    }

    public static void addGeneric(GenericBase genericBase) {
        lstGeneric.add(genericBase);
    }

    public static void removeGeneric(GenericBase genericBase) {
        lstGeneric.remove(genericBase);
    }

    private static Table findTableByName(String tableName, String tableSchema) throws Exception {
        if (null == tableName || "".equals(tableName.trim())) { return null; }
        if (null == tableSchema) { return null; }

        String sql = "select \n" +
                "t.table_name, t.table_comment, c.column_name, c.ordinal_position, c.data_type, c.column_key, c.column_comment \n" +
                "from columns c \n" +
                "left join tables t on (c.table_name = t.table_name and c.table_schema = t.table_schema)\n" +
                "where upper(c.table_name) = '" + tableName.toUpperCase() + "' and upper(c.table_schema) = '" + tableSchema.toUpperCase() + "' " +
                "order by c.ordinal_position";

        Connection conn = null;
        PreparedStatement ps = null;
        Table table = null;
        try {
            conn = ConnUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Column> lstColumn = new ArrayList<Column>();
            int index = 1;
            while (rs.next()) {
                if (index == 1) {
                    table = new Table(rs.getString("table_name"), rs.getString("table_comment"));
                }

                Column column = new Column(rs.getString("column_name"),
                        rs.getString("ordinal_position"),
                        rs.getString("data_type"),
                        rs.getString("column_key"),
                        rs.getString("column_comment"));
                lstColumn.add(column);

                index ++;
            }

            if (null != table)
                table.setLstColumn(lstColumn);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ps) ps.close();
            if (null != conn) conn.close();
        }

        return table;
    }


}
