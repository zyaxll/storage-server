/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.storage.util;

/**
 * //TODO
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version Dec 3, 2015 8:38:55 PM
 */
public abstract class BooleanUtils {

    private BooleanUtils(){}

    public static boolean parse(Object value){
        if(value instanceof Boolean){
            return Boolean.TRUE.equals(value);
        } else if(value instanceof Number){
            return ((Number)value).intValue() == 1;
        }else{
            return (value != null && "true".equalsIgnoreCase(value.toString()));
        }
    }
    
    public static Boolean valueOf(Object value){
        return parse(value) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Boolean[] toArray(String data, String split){
        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(split);
        int i, len = arr.length;
        Boolean[] dataArr = new Boolean[len];
        for(i = 0; i < len; ++i){
            dataArr[i] = Boolean.valueOf(arr[i]);
        }
        return dataArr;
    }

}
