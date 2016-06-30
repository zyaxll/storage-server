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
public class GenericServiceImpl extends BizAdapt {

    private static final String NAME = "ServiceImpl";

    public GenericServiceImpl(Table table) {
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

        String targetName = Constant.SERVICE_PATH;
        if (null == targetName || "".equals(targetName.trim())) {
            targetName = "/src/main/java/com/b5m/" + getProjectName() + "/service/";
        }
        setTargetName(targetName + "/impl/" + getTable().getName() + "Service.java");
    }

    @Override
    protected String replace(StringBuilder source) {
        return super.replace(source).replaceAll("#ltable#", getTable().getLname());
    }

}
