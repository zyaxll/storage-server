package com.b5m.core.entity;

import com.b5m.utils.DateUtils;
import com.b5m.utils.ObjectUtils;
import com.b5m.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @description: 查询条件
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-28
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-28       Leo.li          1.0             TODO
 */

public class Condition implements Serializable {

    /**
     * 列名
     */
    private String key;

    /**
     * 值
     */
    private Object value;

    /**
     * 查询方式
     */
    private Mode mode;

    private Class<?> clazz;

    private List<Condition> cnds;

    public Condition(String key, Object value) {
        this.key = key;
        this.value = value;
        this.mode = Mode.EQ;
        add(this);
    }

    public Condition(String key, Object value, Mode mode) {
        this.key = key;
        this.value = value;
        this.mode = mode;
        add(this);
    }

    public Condition add(Condition condition) {
        if (null == cnds) {
            cnds = new ArrayList<>();
        }
        cnds.add(condition);

        return this;
    }

    public static Condition nullCnd(String key) {
        return new Condition(key, null, Mode.NULL);
    }

    public static Condition notNull(String key) {
        return new Condition(key, null, Mode.NOTNULL);
    }

    public static Condition eq(String key, Object value) {
        return new Condition(key, value);
    }

    public static Condition ne(String key, Object value) {
        return new Condition(key, value, Mode.NE);
    }

    public static Condition like(String key, Object value) {
        return new Condition(key, value, Mode.LIKE);
    }

    public static Condition likeStart(String key, Object value) {
        return new Condition(key, value, Mode.LIKESTART);
    }

    public static Condition likeEnd(String key, Object value) {
        return new Condition(key, value, Mode.LIKEEND);
    }

    public static Condition ilike(String key, Object value) {
        return new Condition(key, value, Mode.ILIKE);
    }

    public static Condition ilikeStart(String key, Object value) {
        return new Condition(key, value, Mode.ILIKESTART);
    }

    public static Condition ilikeEnd(String key, Object value) {
        return new Condition(key, value, Mode.ILIKEEND);
    }

    public static Condition gt(String key, Object value) {
        return new Condition(key, value, Mode.GT);
    }

    public static Condition ge(String key, Object value) {
        return new Condition(key, value, Mode.GTE);
    }

    public static Condition lt(String key, Object value) {
        return new Condition(key, value, Mode.LT);
    }

    public static Condition le(String key, Object value) {
        return new Condition(key, value, Mode.LTE);
    }

    public static <E> Condition in(String key, Collection<E> value) {
        return new Condition(key, value, Mode.IN);
    }

    public static <E> Condition in(String key, E[] value) {
        return new Condition(key, value, Mode.IN);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<Condition> getCnds() {
        return cnds;
    }

    public void setCnds(List<Condition> cnds) {
        this.cnds = cnds;
    }

    public String parseValue() {
        if (value == null) {
            return null;
        } else if (value instanceof String || value instanceof Character) {
            return String.format("'%s'", value);
        } else if (value instanceof Date) {
            return String.format("'%s'", DateUtils.Date2String((Date) value));
        } else {
            return value.toString();
        }

    }

    @Override
    public String toString() {
        String result;
        switch (getMode()) {
            case NULL:
                result = getKey() + " is null";
                break;
            case NOTNULL:
                result = getKey() + " is not null";
                break;
            case EQ:
                result = getKey() + " = " + parseValue();
                break;
            case NE:
                result = getKey() + " != " + parseValue();
                break;
            case LIKE:
                result = getKey() + " like '%" + getValue() + "%'";
                break;
            case LIKESTART:
                result = getKey() + " like '" + getValue() + "%'";
                break;
            case LIKEEND:
                result = getKey() + " like '%" + getValue() + "'";
                break;
            case ILIKE:
                result = "upper(" + getKey() + ") like upper('%" + getValue() + "%')";
                break;
            case ILIKESTART:
                result = "upper(" + getKey() + ") like upper('" + getValue() + "%')";
                break;
            case ILIKEEND:
                result = "upper(" + getKey() + ") like upper('%" + getValue() + "')";
                break;
            case GT:
                result = getKey() + " > " + parseValue();
                break;
            case GTE:
                result = getKey() + " >= " + parseValue();
                break;
            case LT:
                result = getKey() + " < " + parseValue();
                break;
            case LTE:
                result = getKey() + " <= " + parseValue();
                break;
            case IN:
                if (getValue() == null) {
                    return "";
                }

                if (getValue() instanceof Collection) {
                    result = getKey() + " in (" + StringUtils.collectionToCommaDelimitedString((Collection) getValue()) + ")";
                } else if (ObjectUtils.isArray(getValue())) {
                    Object[] objArray = ObjectUtils.toObjectArray(getValue());
                    result = getKey() + " in (" + StringUtils.arrayToDelimitedString(objArray, ",") + ")";
                } else {
                    result = getKey() + " = " + getValue();
                }
                break;
            default:
                result = getKey() + " = " + parseValue();
                break;
        }


        return result;
    }
}
