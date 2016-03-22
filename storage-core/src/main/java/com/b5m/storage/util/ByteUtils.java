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
public abstract class ByteUtils {

    public static final byte ZERO = 0;
    public static final byte ONE = 1;

    private ByteUtils(){}

    public static byte parse(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof Number){
            return ((Number)value).byteValue();
        }else{
            if(value instanceof Boolean){
                return (((Boolean)value) ? ONE : ZERO);
            } else {
                return Byte.parseByte(value.toString());
            }
        }
    }
    
    public static byte parse(Object value, byte defaultValue){
        if(value == null){
            return defaultValue;
        }
        return parse(value);
    }
    
    public static Byte valueOf(Object value){
        if(value == null){
            return null;
        } else if(value instanceof Byte){
            return (Byte)value;
        }
        return parse(value);
    }

    public static Byte[] toArray(String data, String split){
        if(data == null || data.length() == 0){
            return null;
        }
        String[] arr = data.split(split);
        int i, len = arr.length;
        Byte[] dataArr = new Byte[len];
        for(i = 0; i < len; ++i){
            dataArr[i] = Byte.valueOf(arr[i]);
        }
        return dataArr;
    }

    public static boolean gt(Byte source, byte target){
        return source != null && source > target;
    }

    public static boolean gt(byte source, Byte target){
        return target == null || source > target;
    }

    public static boolean gt0(Byte source){
        return gt(source, ZERO);
    }

    /**
     * null is less than all byte value
     * @param source
     * @param target
     * @return
     */
    public static boolean lt(Byte source, byte target){
        return source == null || source < target;
    }

    public static boolean lt(byte source, Byte target){
        return target != null && source < target;
    }

    /**
     * null is less than all byte value
     * @param source
     * @param target
     * @return
     */
    public static boolean lte(Byte source, byte target){
        return source == null || source <= target;
    }

    public static boolean lte(byte source, Byte target){
        return target != null && source <= target;
    }

    public static boolean lt0(Byte source){
        return lt(source, ZERO);
    }
    public static boolean lt1(Byte source){
        return lt(source, ONE);
    }
    public static boolean lte0(Byte source){
        return lte(source, ZERO);
    }
    public static boolean lte1(Byte source){
        return lte(source, ONE);
    }
}
