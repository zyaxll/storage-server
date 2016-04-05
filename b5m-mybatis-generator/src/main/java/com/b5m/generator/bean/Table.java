package com.b5m.generator.bean;

import java.util.List;

/**
 * @description: ${TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 2015/12/16
 */
public class Table {

    private String originalName;
    private String name;
    private String lname;
    private String uname;
    private String comment;

    private List<Column> lstColumn;

    public Table() { }

    public Table(String originalName, String comment) {
        this.originalName = originalName;
        this.comment = comment;

        originalName = originalName.replace("t_core", "");
        this.uname = originalName;

        String[] names = originalName.split("_");
        originalName = "";
        for (String name : names) {
            if ("".equals(name)) {
                continue;
            }
            originalName += name.substring(0,1).toUpperCase() + name.substring(1);
        }

        this.name = originalName;
        this.lname = originalName.substring(0,1).toLowerCase() + originalName.substring(1);
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getLstColumn() {
        return lstColumn;
    }

    public void setLstColumn(List<Column> lstColumn) {
        this.lstColumn = lstColumn;
    }
}
