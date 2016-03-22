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
public abstract class ShortUtils {

    private ShortUtils(){}

    public static final short ZERO = 0;
    public static final short ONE = 1;

    public static short parse(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof Number){
            return ((Number)value).shortValue();
        }else{
            if(value instanceof Boolean){
                return (((Boolean)value) ? ONE : ZERO);
            } else {
                return Short.parseShort(value.toString());
            }
        }
    }
    
    public static short parse(Object value, short defaultValue){
        if(value == null){
            return defaultValue;
        }
        return parse(value);
    }
    
    public static Short valueOf(Object value){
        if(value == null){
            return null;
        } else if(value instanceof Short){
            return (Short)value;
        }
        return parse(value);
    }

    public static Short[] toArray(String data, String split){
        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(split);
        int i, len = arr.length;
        Short[] dataArr = new Short[len];
        for(i = 0; i < len; ++i){
            dataArr[i] = Short.valueOf(arr[i]);
        }
        return dataArr;
    }

    public static boolean gt(Short source, short target){
        return source == null ? false : source.shortValue() > target;
    }

    public static boolean gt(short source, Short target){
        return target == null ? true : source > target.shortValue();
    }

    public static boolean gt0(Short source){
        return gt(source, ZERO);
    }

    /**
     * null is less than all short value
     * @param source
     * @param target
     * @return
     */
    public static boolean lt(Short source, short target){
        return source == null ? true : source.shortValue() < target;
    }

    public static boolean lt(short source, Short target){
        return target == null ? false : source < target.shortValue();
    }

    /**
     * null is less than all short value
     * @param source
     * @param target
     * @return
     */
    public static boolean lte(Short source, short target){
        return source == null ? true : source.shortValue() <= target;
    }

    public static boolean lte(short source, Short target){
        return target == null ? false : source <= target.shortValue();
    }

    public static boolean lt0(Short source){
        return lt(source, ZERO);
    }
    public static boolean lt1(Short source){
        return lt(source, ONE);
    }
    public static boolean lte0(Short source){
        return lte(source, ZERO);
    }
    public static boolean lte1(Short source){
        return lte(source, ONE);
    }
}
