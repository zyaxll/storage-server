/**
 * //TODO
 */
package com.b5m.storage.util;

import java.math.BigDecimal;

/**
 * 人民币计算工具类
 * @author lucky.liu
 * @email liuwb2010@gmail.com
 * @version May 28, 2015 3:08:52 PM
 */
public abstract class MoneyUtils {

    private MoneyUtils(){}

    /**
     * 根据元(人民币)的价格获取分(人民币)
     * @param value 元(人民币)，最多两位小数
     * @return 分(人民币)的值
     */
    public static long getCents(Float value){
        return value == null ? 0L : Math.round(value.doubleValue() * 100d);
    }
    
    public static int getIntCents(Float value){
        return (int)getCents(value);
    }
    
    /**
     * 根据元(人民币)的价格获取分(人民币)
     * @param value 元(人民币)，最多两位小数
     * @return 分(人民币)的值
     */
    public static long getCents(BigDecimal value){
        return value == null ? 0L : Math.round(value.doubleValue() * 100d);
    }

    public static int getIntCents(BigDecimal value){
        return (int)getCents(value);
    }
    
    public static float getFloatCents(BigDecimal value){
        return (float)getCents(value);
    }

}
