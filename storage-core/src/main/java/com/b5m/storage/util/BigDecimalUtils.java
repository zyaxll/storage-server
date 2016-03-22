/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.storage.util;

import java.math.BigDecimal;

/**
 * //TODO
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version Dec 17, 2015 2:29:40 PM
 */
public class BigDecimalUtils {

    public static BigDecimal valueOf(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof BigDecimal){
            return (BigDecimal)value;
        } else if(value instanceof Number){
            return BigDecimal.valueOf(((Number)value).doubleValue());
        }else{
            if(value instanceof Boolean){
                return Boolean.TRUE.equals(value) ? BigDecimal.ONE : BigDecimal.ZERO;
            } else {
                return new BigDecimal(value.toString());
            }
        }
    }
    
}
