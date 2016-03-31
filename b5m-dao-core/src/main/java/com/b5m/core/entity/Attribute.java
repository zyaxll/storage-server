package com.b5m.core.entity;

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
public class Attribute implements Iterable<Attribute.Property>, Serializable {

    private List<Property> properties = new ArrayList<>();

    public Attribute(Property... properties) {
        this(properties == null ? new ArrayList<Property>() : Arrays.asList(properties));
    }

    public Attribute(List<Property> properties) {
        if (null != properties) {
            this.properties.addAll(properties);
        }
    }

    public Attribute add(Property property) {
        if (null == property) {
            return this;
        }

        this.properties.add(property);

        return this;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public Iterator<Property> iterator() {
        return this.properties.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute that = (Attribute) o;

        return properties != null ? properties.equals(that.properties) : that.properties == null;

    }

    @Override
    public int hashCode() {
        return properties != null ? properties.hashCode() : 0;
    }

    @Override
    public String toString() {
        return StringUtils.collectionToCommaDelimitedString(properties);
    }

    public static class Property implements Serializable {

        private String name;
        private Object value;

        public Property(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Property property = (Property) o;

            return name != null ? name.equals(property.name) : property.name == null && (value != null ? value.equals(property.value) : property.value == null);

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return String.format("%s = %s", name, value);
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
    }

}
