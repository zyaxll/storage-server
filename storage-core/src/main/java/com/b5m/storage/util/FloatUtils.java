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
public abstract class FloatUtils {

    public static final float ZERO = 0f;
    public static final float ONE = 1f;

    private FloatUtils(){}

    public static float parse(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof Number){
            return ((Number)value).floatValue();
        }else{
            if(value instanceof Boolean){
                return (((Boolean)value) ? ONE : ZERO);
            } else {
                return Float.parseFloat(value.toString());
            }
        }
    }
    
    public static float parse(Object value, float defaultValue){
        if(value == null){
            return defaultValue;
        }
        return parse(value);
    }
    
    public static Float valueOf(Object value){
        if(value == null){
            return null;
        } else if(value instanceof Float){
            return (Float)value;
        }
        return parse(value);
    }

    public static Float[] toArray(String data, String split){
        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(split);
        int i, len = arr.length;
        Float[] dataArr = new Float[len];
        for(i = 0; i < len; ++i){
            dataArr[i] = Float.valueOf(arr[i]);
        }
        return dataArr;
    }

    public static boolean gt(Float source, float target){
        return source == null ? false : source.floatValue() > target;
    }

    public static boolean gt(float source, Float target){
        return target == null ? true : source > target.floatValue();
    }

    public static boolean gt0(Float source){
        return gt(source, ZERO);
    }

    /**
     * null is less than all float value
     * @param source
     * @param target
     * @return
     */
    public static boolean lt(Float source, float target){
        return source == null ? true : source.floatValue() < target;
    }

    public static boolean lt(float source, Float target){
        return target == null ? false : source < target.floatValue();
    }

    /**
     * null is less than all float value
     * @param source
     * @param target
     * @return
     */
    public static boolean lte(Float source, float target){
        return source == null ? true : source.floatValue() <= target;
    }

    public static boolean lte(float source, Float target){
        return target == null ? false : source <= target.floatValue();
    }

    public static boolean lt0(Float source){
        return lt(source, ZERO);
    }
    public static boolean lt1(Float source){
        return lt(source, ONE);
    }
    public static boolean lte0(Float source){
        return lte(source, ZERO);
    }
    public static boolean lte1(Float source){
        return lte(source, ONE);
    }
}
