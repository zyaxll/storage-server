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
public abstract class IntUtils {

    public static final int ZERO = 0;
    public static final int ONE = 1;

    private IntUtils(){}

    public static int parse(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof Number){
            return ((Number)value).intValue();
        }else{
            if(value instanceof Boolean){
                return ((Boolean)value) ? ONE : ZERO;
            } else {
                return Integer.parseInt(value.toString());
            }
        }
    }
    
    public static int parse(Object value, int defaultValue){
        if(value == null){
            return defaultValue;
        }
        return parse(value);
    }
    
    public static Integer valueOf(Object value){
        if(value == null){
            return null;
        } else if(value instanceof Integer){
            return (Integer)value;
        }
        return parse(value);
    }

    public static Integer[] toArray(String data, String split){
        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(split);
        int i, len = arr.length;
        Integer[] dataArr = new Integer[len];
        for(i = 0; i < len; ++i){
            dataArr[i] = Integer.valueOf(arr[i]);
        }
        return dataArr;
    }

    public static boolean gt(Integer source, int target){
        return source == null ? false : source.intValue() > target;
    }

    public static boolean gt(int source, Integer target){
        return target == null ? true : source > target.intValue();
    }

    public static boolean gt0(Integer source){
        return gt(source, ZERO);
    }

    /**
     * null is less than all int value
     * @param source
     * @param target
     * @return
     */
    public static boolean lt(Integer source, int target){
        return source == null ? true : source.intValue() < target;
    }

    public static boolean lt(int source, Integer target){
        return target == null ? false : source < target.intValue();
    }

    /**
     * null is less than all int value
     * @param source
     * @param target
     * @return
     */
    public static boolean lte(Integer source, int target){
        return source == null ? true : source.intValue() <= target;
    }

    public static boolean lte(int source, Integer target){
        return target == null ? false : source <= target.intValue();
    }

    public static boolean lt0(Integer source){
        return lt(source, ZERO);
    }
    public static boolean lt1(Integer source){
        return lt(source, ONE);
    }
    public static boolean lte0(Integer source){
        return lte(source, ZERO);
    }
    public static boolean lte1(Integer source){
        return lte(source, ONE);
    }
}
