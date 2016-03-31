package com.b5m.core.entity;

import com.b5m.utils.Assert;
import com.b5m.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-30
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-30       Leo.li          1.0             TODO
 */
public class Attribute implements Serializable {

    private String name;
    private Object value;

    private List<Attribute> attrs;

    public Attribute(String name, Object value) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Attribute's name is null");
        }

        this.name = name;
        this.value = value;

        add(this);
    }

    public Attribute add(Attribute attribute) {
        if (this.attrs == null) {
            this.attrs = new ArrayList<>();
        }

        if (null == attribute) {
            return this;
        }

        this.attrs.add(attribute);

        return this;
    }

    public static Attribute NEW(String name, Object value) {
        return new Attribute(name, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<Attribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<Attribute> attrs) {
        this.attrs = attrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        return value != null ? value.equals(attribute.value) : attribute.value == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
