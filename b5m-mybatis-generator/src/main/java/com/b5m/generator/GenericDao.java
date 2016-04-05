package com.b5m.generator;

import com.b5m.generator.core.BizAdapt;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class GenericDao extends BizAdapt {

    private static final String NAME = "Mapper";

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public void setFilePath(String filePath) {
        super.setFilePath(filePath + "/storage-core");
    }

    @Override
    protected void init() {
        setSourceName(getSourcePath() + "template/" + getName());
        setTargetName("/src/main/java/com/b5m/storage/dao/" + getTable().getName() + getName() + ".java");
    }

}
