package com.b5m.generator;

import com.b5m.generator.bean.Table;
import com.b5m.generator.core.BizAdapt;
import com.b5m.generator.utils.Constant;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class GenericDao extends BizAdapt {

    private static final String NAME = "Mapper";

    public GenericDao(Table table) {
        super(table);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public void setFilePath(String filePath) {
        super.setFilePath(filePath + "/" + getProjectName() + "-core");
    }

    @Override
    protected void init() {
        setSourceName(getSourcePath() + "template/" + getName());

        String targetName = Constant.DAO_PATH;
        if (null == targetName || "".equals(targetName.trim())) {
            targetName = "/src/main/java/com/b5m/" + getProjectName() + "/dao/";
        }
        setTargetName(targetName + getTable().getName() + getName() + ".java");
    }

}
