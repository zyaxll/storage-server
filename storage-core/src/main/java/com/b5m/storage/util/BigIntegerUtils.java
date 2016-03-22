/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.storage.util;

import java.math.BigInteger;

/**
 * //TODO
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version Dec 17, 2015 2:29:40 PM
 */
public class BigIntegerUtils {

    public static BigInteger valueOf(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof Number){
            return BigInteger.valueOf(((Number)value).longValue());
        }else{
            if(value instanceof Boolean){
                return Boolean.TRUE.equals(value) ? BigInteger.ONE : BigInteger.ZERO;
            } else {
                return new BigInteger(value.toString());
            }
        }
    }
    
}
