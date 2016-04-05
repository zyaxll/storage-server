package com.b5m.generator.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class ConnUtils {

    private static String JDBC_DRIVER;
    private static String JDBC_URL;
    private static String USER_NAME;
    private static String PASSWORD;

    static {
        try {
            Properties properties = PropertyUtils.load(ConnUtils.class.getClassLoader(), "config");
            JDBC_DRIVER = PropertyUtils.getValue("jdbc.driver", properties);
            JDBC_URL = PropertyUtils.getValue("jdbc.url", properties);
            USER_NAME = PropertyUtils.getValue("jdbc.username", properties);
            PASSWORD = PropertyUtils.getValue("jdbc.password", properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

}
