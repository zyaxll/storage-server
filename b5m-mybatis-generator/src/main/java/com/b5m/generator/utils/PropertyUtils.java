package com.b5m.generator.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-4-5
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-4-5       Leo.li          1.0             TODO
 */
public class PropertyUtils {

    /**
     * 搜索和读取classpath下指定配置文件内容(总是不会返回null)
     *
     * @param classLoader 类加载器
     * @param config 配置文件名(eq.config.properties)
     * @return 读取到的Properties配置信息,如果配置文件不存在则返回空的properties对象
     * @throws IOException IO错误
     */
    public static Properties load(ClassLoader classLoader, String config) throws IOException {
        Properties properties = new Properties();
        Enumeration<URL> urls = null;
        if(classLoader != null){
            urls = classLoader.getResources(config);
        }else {
            urls = ClassLoader.getSystemResources(config);
        }
        if (urls != null) {
            if(urls.hasMoreElements()) {
                properties.load(urls.nextElement().openStream());
            }
        }
        return properties;
    }

    public static String getValue(String key, Properties properties) {
        if (null == key || "".equals(key)) {
            return "";
        }
        String val = properties.getProperty(key);

        if (null == val || "".equals(val.trim())) {
            throw new RuntimeException(String.format("property [%s] is not exist", key));
        }

        return val.trim();
    }

    public static String getValue(String key, Properties properties, boolean isMust) {
        if (null == key || "".equals(key)) {
            return "";
        }
        String val = properties.getProperty(key);

        if ((null == val || "".equals(val.trim())) && isMust) {
            throw new RuntimeException(String.format("property [%s] is not exist", key));
        }

        return val;
    }

}
