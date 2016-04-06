package com.b5m.generator.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class ConnUtils {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(Constant.JDBC_DRIVER);
            conn = DriverManager.getConnection(Constant.JDBC_URL, Constant.USER_NAME, Constant.PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

}
