package com.b5m.generator;

import com.b5m.generator.bean.Column;
import com.b5m.generator.bean.Table;
import com.b5m.generator.core.GenericBase;
import com.b5m.generator.core.GenericType;
import com.b5m.generator.utils.ConnUtils;
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
            generic(tableName, "b5m_develop");
        }
    }

    public static void generic(String tableName) {
        generic(tableName, "b5m_develop");
    }

    public static void generic(String tableName, String tableSchema) {
        generic(tableName, tableSchema, "/home/leo/Pro/storage-server");
    }

    public static void generic(String tableName, String tableSchema, String filePath) {
        generic(tableName, tableSchema, filePath, "Leo.li");
    }

    public static void generic(String tableName, String tableSchema, String filePath, String author) {
        try {
            Properties properties = PropertyUtils.load(GenericFactory.class.getClassLoader(), "config");
            tableSchema = PropertyUtils.getValue("table_schema", properties);
            filePath = PropertyUtils.getValue("file_path", properties);
            author = PropertyUtils.getValue("common_author", properties);

            Table table = findTableByName(tableName, tableSchema);

            addGeneric(genericBean(GenericType.ENTITY));
            addGeneric(genericBean(GenericType.DAO));
            addGeneric(genericBean(GenericType.SERVICE));
            addGeneric(genericBean(GenericType.SERVICE_IMPL));

            if (null != table) {
                genericJava(table, filePath, author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void genericJava(Table table, String filePath, String author) {
        try {
            for (GenericBase generic : lstGeneric) {
                generic.setFilePath(filePath);
                generic.setTable(table);
                generic.setAuthor(author);
                generic.generic();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GenericBase genericBean(GenericType type) {
        GenericBase base;
        switch (type) {
            case ENTITY:
                base = new GenericEntity();
                break;
            case DAO:
                base = new GenericDao();
                break;
            case SERVICE:
                base = new GenericService();
                break;
            case SERVICE_IMPL:
                base = new GenericServiceImpl();
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
