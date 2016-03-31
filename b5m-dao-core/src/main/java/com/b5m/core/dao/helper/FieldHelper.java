package com.b5m.core.dao.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description: 属性映射Helper 从原EntityHelper提出来
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: chonglou
 * @version: 1.0
 * @createdate: 2015/12/31
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015/12/31  chonglou          1.0             Why & What is modified
 */
public class FieldHelper {

    /**
     * 获取Class、包括父类（直到Object）全部的Field
     *
     * @param entityClass
     * @param fieldList
     * @return
     */
    public static List<Field> getAllField(Class<?> entityClass, List<Field> fieldList) {
        if (fieldList == null) {
            fieldList = new ArrayList<>();
        }
        if (entityClass.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            //排除静态字段,如serialVersionUID
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        if (entityClass.getSuperclass() != null
                && !entityClass.getSuperclass().equals(Object.class)
                && !Map.class.isAssignableFrom(entityClass.getSuperclass())
                && !Collection.class.isAssignableFrom(entityClass.getSuperclass())) {
            return getAllField(entityClass.getSuperclass(), fieldList);
        }
        return fieldList;
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelHumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
                (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isLowercaseAlpha(c)) {
                sb.append(toUpperAscii(c));
            } else {
                sb.append('_').append(c);
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     */
    @Deprecated
    public static String underlineToCamelhump(String name) {
        char[] buffer = name.toCharArray();
        int count = 0;
        boolean lastUnderscore = false;
        for (int i = 0; i < buffer.length; i++) {
            char c = buffer[i];
            if (c == '_') {
                lastUnderscore = true;
            } else {
                c = (lastUnderscore && count != 0) ? toUpperAscii(c) : toLowerAscii(c);
                buffer[count++] = c;
                lastUnderscore = false;
            }
        }
        if (count != buffer.length) {
            buffer = subarray(buffer, 0, count);
        }
        return new String(buffer);
    }

    public static char[] subarray(char[] src, int offset, int len) {
        char[] dest = new char[len];
        System.arraycopy(src, offset, dest, 0, len);
        return dest;
    }

    public static boolean isLowercaseAlpha(char c) {
        return (c >= 'a') && (c <= 'z');
    }

    public static char toUpperAscii(char c) {
        if (isLowercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }

    public static char toLowerAscii(char c) {
        if ((c >= 'A') && (c <= 'Z')) {
            c += (char) 0x20;
        }
        return c;
    }
}
