package com.b5m.generator;

import com.b5m.generator.bean.Column;
import com.b5m.generator.bean.Table;
import com.b5m.generator.core.ColumnAdapt;
import com.b5m.generator.utils.Constant;

/**
 * @description: {TODO}
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: ${Date}
 */
public class GenericEntity extends ColumnAdapt {

    private static final String NAME = "Entity";
    private static final String RP_TABLE_U = "#utable#";
    private static final String RP_TABLE_N = "#tableName#";
    private static final String RP_TABLE_O = "#utableName#";

    public GenericEntity(Table table) {
        super(table);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    protected void init() {
        setSourceName(getSourcePath() + "template/" + getName());

        String targetName = Constant.ENTITY_PATH;
        if (null == targetName || "".equals(targetName.trim())) {
            targetName = "/src/main/java/com/b5m/" + getProjectName() + "/model/entity/";
        }
        setTargetName(targetName + getTable().getName() + ".java");
    }

    @Override
    public void setFilePath(String filePath) {
        super.setFilePath(filePath + "/" + getProjectName() + "-api");
    }

    @Override
    protected void getAnno(StringBuilder target, Column column) {
        if ("PRI".equals(column.getColumnKey().toUpperCase())) {
            target.append(Constant.FILE_SPACE)
                    .append("@Id")
                    .append(Constant.FILE_LINE)
                    .append(Constant.FILE_SPACE)
                    .append("@GeneratedValue")
                    .append(Constant.FILE_LINE);
        }

        target.append(Constant.FILE_SPACE)
                .append("@Column(name = ")
                .append(column.getOriginalName().toUpperCase())
                .append("_COLUMN)")
                .append(Constant.FILE_LINE)
                .append(Constant.FILE_SPACE);
    }

    @Override
    protected void generic(StringBuilder target) throws Exception {
        StringBuilder method = new StringBuilder();
        for (Column column : getTable().getLstColumn()) {
            getComment(target, column.getComment());
            getAnno(target, column);
            getProperty(target, column);

            getPropertyConst(target, column);

            getter(method, column);
            setter(method, column);
        }

        target.append(method).append("}");
    }

    @Override
    protected String replace(StringBuilder source) {
        return super.replace(source)
                .replaceAll(RP_TABLE_U, getTable().getUname().toUpperCase())
                .replaceAll(RP_TABLE_N, getTable().getOriginalName())
                .replaceAll(RP_TABLE_O, getTable().getOriginalName().toUpperCase());
    }

    private void getPropertyConst(StringBuilder target, Column column) {
        target.append(Constant.FILE_SPACE)
                .append("public static final String ")
                .append(column.getOriginalName().toUpperCase())
                .append("_PROPERTY = \"")
                .append(column.getName())
                .append("\";")
                .append(Constant.FILE_LINE)
                .append(Constant.FILE_SPACE)
                .append("public static final String ")
                .append(column.getOriginalName().toUpperCase())
                .append("_COLUMN = \"")
                .append(column.getOriginalName())
                .append("\";")
                .append(Constant.FILE_LINE)
                .append(Constant.FILE_LINE);
    }

}
