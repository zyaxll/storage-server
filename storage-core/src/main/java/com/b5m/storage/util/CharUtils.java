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
public abstract class CharUtils {

    private CharUtils(){}

    public static char parse(Object value){
        if(value instanceof Character){
            return (Character)value;
        }else if(value instanceof Number){
            return (char)((Number) value).intValue();
        }else{
            return value.toString().charAt(0);
        }
    }
    
    public static char parse(Object value, char defaultValue){
        if(value == null){
            return defaultValue;
        }
        return parse(value);
    }
    
    public static Character valueOf(Object value){
        if(value == null){
            return null;
        } else if(value instanceof Character){
            return (Character)value;
        }
        return parse(value);
    }

    public static Character[] toArray(String data, String split){
        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(split);
        int i, len = arr.length;
        Character[] dataArr = new Character[len];
        for(i = 0; i < len; ++i){
            String chr = arr[i];
            if(chr.length() != 1){
                throw new IllegalArgumentException(chr + " is not a char value!");
            }
            dataArr[i] = Character.valueOf(chr.charAt(0));
        }
        return dataArr;
    }

    public static boolean gt(Character source, char target){
        return source == null ? false : source.charValue() > target;
    }

    public static boolean gt(char source, Character target){
        return target == null ? true : source > target.charValue();
    }

    /**
     * null is less than all char value
     * @param source
     * @param target
     * @return
     */
    public static boolean lt(Character source, char target){
        return source == null ? true : source.charValue() < target;
    }

    public static boolean lt(char source, Character target){
        return target == null ? false : source < target.charValue();
    }

    /**
     * null is less than all char value
     * @param source
     * @param target
     * @return
     */
    public static boolean lte(Character source, char target){
        return source == null ? true : source.charValue() <= target;
    }

    public static boolean lte(char source, Character target){
        return target == null ? false : source <= target.charValue();
    }

}
