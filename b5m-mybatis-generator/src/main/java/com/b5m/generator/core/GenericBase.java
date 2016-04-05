package com.b5m.generator.core;

import com.b5m.generator.bean.Table;
import com.b5m.generator.utils.Constant;
import com.b5m.generator.utils.FileUtils;
import com.b5m.generator.utils.PropertyUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public abstract class GenericBase {

    protected static final String RP_TABLE = "#table#";
    protected static final String RP_TABLE_COMMENT = "#tableComment#";
    protected static final String RP_AUTHOR = "#author#";
    protected static final String RP_DATE = "#date#";

    private String name;
    private String filePath;
    private String sourcePath;
    private String sourceName;
    private String targetName;
    private String author;
    private String date = Constant.CURR_DATE;
    private Table table;
    private String projectName;

    private Properties properties;

    public GenericBase() {
        try {
            this.properties = PropertyUtils.load(this.getClass().getClassLoader(), "config.properties");
        } catch (IOException e) {
            throw new RuntimeException("can't load properties config..", e);
        }
        this.author = PropertyUtils.getValue("common_author", this.properties);
        this.projectName = PropertyUtils.getValue("project_name", this.properties);
        this.filePath = PropertyUtils.getValue("file_path", this.properties);
    }

    public GenericBase(Table table) {
        this();
        init();
        this.table = table;
    }

    public GenericBase(String filePath, String author, Table table) {
        this();
        this.filePath = filePath;
        this.author = author;
        this.table = table;
    }

    public void generic() throws Exception {
        StringBuilder source = FileUtils.readFile(getSourceName());

        if (source.length() == 0) {
            throw new Exception("模板文件为空.");
        }
        StringBuilder target = new StringBuilder(replace(source));
        generic(target);
        FileUtils.writeFile(getTargetName(), target);
    }

    protected abstract void init();

    protected abstract void generic(StringBuilder target) throws Exception;

    protected String replace(StringBuilder source) {
        return source.toString()
                .replaceAll(RP_TABLE, getTable().getName())
                .replaceAll(RP_TABLE_COMMENT, getTable().getComment())
                .replaceAll(RP_AUTHOR, getAuthor())
                .replaceAll(RP_DATE, getDate());
    }

    protected abstract String getName();

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSourcePath() {
        if (null == sourcePath) {
            URL url = this.getClass().getClassLoader().getResource(".");
            if (null != url) {
                this.sourcePath = url.getPath();
            }
        }
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourceName() {
        return sourceName;
    }

    protected void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        if (null != getFilePath() && !"".equals(getFilePath())) {
            return getFilePath() + targetName;
        }
        return targetName;
    }

    protected void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
        init();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
