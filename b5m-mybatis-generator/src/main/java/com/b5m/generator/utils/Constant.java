package com.b5m.generator.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class Constant {

    public final static String FILE_SPACE = "    ";
    public final static String FILE_LINE = "#line#";
    public final static String CURR_DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

    public static String JDBC_DRIVER;
    public static String JDBC_URL;
    public static String USER_NAME;
    public static String PASSWORD;
    public static String PROJECT_NAME;
    public static String AUTHOR;
    public static String FILE_PATH;
    public static String TABLE_SCHEMA;
    public static String ENTITY_PATH;
    public static String DAO_PATH;
    public static String SERVICE_PATH;

    static {
        try {
            Properties properties = PropertyUtils.load(ConnUtils.class.getClassLoader(), "config.properties");
            JDBC_DRIVER = PropertyUtils.getValue("jdbc.driver", properties);
            JDBC_URL = PropertyUtils.getValue("jdbc.url", properties);
            USER_NAME = PropertyUtils.getValue("jdbc.username", properties);
            PASSWORD = PropertyUtils.getValue("jdbc.password", properties);
            AUTHOR = PropertyUtils.getValue("common_author", properties);
            PROJECT_NAME = PropertyUtils.getValue("project_name", properties);
            FILE_PATH = PropertyUtils.getValue("file_path", properties);
            TABLE_SCHEMA = PropertyUtils.getValue("table_schema", properties);
            ENTITY_PATH = PropertyUtils.getValue("entity_path", properties, false);
            DAO_PATH = PropertyUtils.getValue("dao_path", properties, false);
            SERVICE_PATH = PropertyUtils.getValue("service_path", properties, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
