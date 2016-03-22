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
public abstract class DoubleUtils {

    public static final double ZERO = 0d;
    public static final double ONE = 1d;

    private DoubleUtils(){}

    public static double parse(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof Number){
            return ((Number)value).doubleValue();
        }else{
            if(value instanceof Boolean){
                return (((Boolean)value) ? ONE : ZERO);
            } else {
                return Double.parseDouble(value.toString());
            }
        }
    }
    
    public static double parse(Object value, double defaultValue){
        if(value == null){
            return defaultValue;
        }
        return parse(value);
    }
    
    public static Double valueOf(Object value){
        if(value == null){
            return null;
        } else if(value instanceof Double){
            return (Double)value;
        }
        return parse(value);
    }

    public static Double[] toArray(String data, String split){
        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(split);
        int i, len = arr.length;
        Double[] dataArr = new Double[len];
        for(i = 0; i < len; ++i){
            dataArr[i] = Double.valueOf(arr[i]);
        }
        return dataArr;
    }

    public static boolean gt(Double source, double target){
        return source == null ? false : source.doubleValue() > target;
    }

    public static boolean gt(double source, Double target){
        return target == null ? true : source > target.doubleValue();
    }

    public static boolean gt0(Double source){
        return gt(source, ZERO);
    }

    /**
     * null is less than all double value
     * @param source
     * @param target
     * @return
     */
    public static boolean lt(Double source, double target){
        return source == null ? true : source.doubleValue() < target;
    }

    public static boolean lt(double source, Double target){
        return target == null ? false : source < target.doubleValue();
    }

    /**
     * null is less than all double value
     * @param source
     * @param target
     * @return
     */
    public static boolean lte(Double source, double target){
        return source == null ? true : source.doubleValue() <= target;
    }

    public static boolean lte(double source, Double target){
        return target == null ? false : source <= target.doubleValue();
    }

    public static boolean lt0(Double source){
        return lt(source, ZERO);
    }
    public static boolean lt1(Double source){
        return lt(source, ONE);
    }
    public static boolean lte0(Double source){
        return lte(source, ZERO);
    }
    public static boolean lte1(Double source){
        return lte(source, ONE);
    }
}
