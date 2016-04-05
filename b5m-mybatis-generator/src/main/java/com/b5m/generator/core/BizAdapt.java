package com.b5m.generator.core;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public abstract class BizAdapt extends GenericBase {

    @Override
    protected void generic(StringBuilder target) throws Exception {

    }

    @Override
    protected String replace(StringBuilder source) {
        return source.toString()
                .replaceAll("#table#", getTable().getName())
                .replaceAll("#tableComment#", getTable().getComment())
                .replaceAll("#author#", getAuthor())
                .replaceAll("#date#", getDate());
    }
}
