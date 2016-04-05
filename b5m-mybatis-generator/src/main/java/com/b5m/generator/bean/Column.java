package com.b5m.generator.bean;

import java.io.Serializable;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class Column implements Serializable {

    private String originalName;
    private String name;
    private String uName;
    private String ordinalPosition;
    private String dataType;
    private String columnKey;
    private String comment;

    public Column() {
    }

    public Column(String originalName, String ordinalPosition, String dataType, String columnKey, String comment) {
        this.originalName = originalName;
        this.ordinalPosition = ordinalPosition;
        this.dataType = dataType;
        this.columnKey = columnKey == null ? "" : columnKey;
        this.comment = comment;

        String[] names = originalName.split("_");
        originalName = "";
        for (String name : names) {
            if ("".equals(name)) {
                continue;
            }
            originalName += name.substring(0,1).toUpperCase() + name.substring(1);
        }
        this.uName = originalName;
        this.name = originalName.substring(0,1).toLowerCase() + originalName.substring(1);

    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(String ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
