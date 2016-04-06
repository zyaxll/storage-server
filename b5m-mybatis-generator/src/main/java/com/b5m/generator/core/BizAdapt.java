package com.b5m.generator.core;

import com.b5m.generator.bean.Table;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public abstract class BizAdapt extends GenericBase {

    public BizAdapt(Table table) {
        super(table);
    }

    @Override
    protected void generic(StringBuilder target) throws Exception {

    }

    @Override
    protected String replace(StringBuilder source) {
        return source.toString()
                .replaceAll(RP_TABLE, getTable().getName())
                .replaceAll(RP_TABLE_COMMENT, getTable().getComment())
                .replaceAll(RP_AUTHOR, getAuthor())
                .replaceAll(RP_PRO, getProjectName())
                .replaceAll(RP_DATE, getDate());
    }
}
