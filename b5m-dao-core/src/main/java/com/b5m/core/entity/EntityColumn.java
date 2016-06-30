package com.b5m.core.entity;

import com.b5m.utils.StringUtils;
import org.apache.ibatis.type.JdbcType;

/**
 * @description: PO对应的每个field
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: chonglou
 * @version: 1.0
 * @createdate: 2015/12/29
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015/12/29  chonglou          1.0             Why & What is modified
 */
public class EntityColumn {

    private EntityTable entityTable;
    private String property;
    private String column;
    private Class<?> javaType;
    private JdbcType jdbcType;

    private String orderBy;

    private boolean id = false;
    private boolean identity = false;

    private boolean insertable = true;
    private boolean updatable = true;

    public EntityColumn() {
    }

    public EntityColumn(EntityTable entityTable) {
        this.entityTable = entityTable;
    }

    public EntityTable getEntityTable() {
        return entityTable;
    }

    public void setEntityTable(EntityTable entityTable) {
        this.entityTable = entityTable;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    /**
     * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @return
     */
    public String getColumnEqualsHolder() {
        return getColumnEqualsHolder(null);
    }

    /**
     * 返回格式如:colum = #{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @param entityName
     * @return
     */
    public String getColumnEqualsHolder(String entityName) {
        return this.column + " = " + getColumnHolder(entityName);
    }

    /**
     * 返回格式如:#{age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @return
     */
    public String getColumnHolder() {
        return getColumnHolder(null);
    }

    /**
     * 返回格式如:#{entityName.age,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @param entityName
     * @return
     */
    public String getColumnHolder(String entityName) {
        return getColumnHolder(entityName, null);
    }

    /**
     * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
     *
     * @param entityName
     * @param suffix
     * @return
     */
    public String getColumnHolder(String entityName, String suffix) {
        return getColumnHolder(entityName, null, null);
    }

    /**
     * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler},
     *
     * @param entityName
     * @param suffix
     * @return
     */
    public String getColumnHolderWithComma(String entityName, String suffix) {
        return getColumnHolder(entityName, suffix, ",");
    }

    /**
     * 返回格式如:#{entityName.age+suffix,jdbcType=NUMERIC,typeHandler=MyTypeHandler}+separator
     *
     * @param entityName
     * @param suffix
     * @param separator
     * @return
     */
    public String getColumnHolder(String entityName, String suffix, String separator) {
        StringBuffer sb = new StringBuffer("#{");
        if (StringUtils.isNotEmpty(entityName)) {
            sb.append(entityName);
            sb.append(".");
        }
        sb.append(this.property);
        if (StringUtils.isNotEmpty(suffix)) {
            sb.append(suffix);
        }
        if (this.jdbcType != null) {
            sb.append(",jdbcType=");
            sb.append(this.jdbcType.toString());
        }

        sb.append("}");
        if (StringUtils.isNotEmpty(separator)) {
            sb.append(separator);
        }
        return sb.toString();
    }
}
