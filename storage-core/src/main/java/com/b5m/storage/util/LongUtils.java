/**
 * //TODO
 */
package com.b5m.storage.util;

/**
 * 字符串处理工具类
 * @author lucky.liu
 * @version v2.0 21.06.2013 09:40:18
 */
public abstract class LongUtils {

	public static final long ZERO = 0L;
	public static final long ONE = 1L;

	private LongUtils(){}

    public static long parse(Object value){
        if(value == null){
            throw new NullPointerException();
        }
        if(value instanceof Number){
            return ((Number)value).longValue();
        }else{
            if(value instanceof Boolean){
                return ((Boolean)value) ? ONE : ZERO;
            } else {
                return Integer.parseInt(value.toString());
            }
        }
    }
    
    public static long parse(Object value, long defaultValue){
        if(value == null){
            return defaultValue;
        }
        return parse(value);
    }
    
    public static Long valueOf(Object value){
        if(value == null){
            return null;
        } else if(value instanceof Long){
            return (Long)value;
        }
        return parse(value);
    }
    
	public static Long[] toArray(String data, String split){
	    if(data == null || data.length() == 0){
	        return null;
	    }
	    String[] arr = data.split(split);
	    int i, len = arr.length;
	    Long[] dataArr = new Long[len];
	    for(i = 0; i < len; ++i){
	        dataArr[i] = Long.valueOf(arr[i]);
	    }
	    return dataArr;
	}
	
	public static boolean gt(Long source, long target){
	    return source == null ? false : source.longValue() > target;
	}
	
	public static boolean gt(long source, Long target){
	    return target == null ? true : source > target.longValue();
	}
	
	public static boolean gt0(Long source){
	    return gt(source, ZERO);
	}
	
	/**
	 * null is less than all long value
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean lt(Long source, long target){
	    return source == null ? true : source.longValue() < target;
	}
	
	public static boolean lt(long source, Long target){
	    return target == null ? false : source < target.longValue();
	}
	
	/**
	 * null is less than all long value
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean lte(Long source, long target){
	    return source == null ? true : source.longValue() <= target;
	}
	
	public static boolean lte(long source, Long target){
	    return target == null ? false : source <= target.longValue();
	}
	
	public static boolean lt0(Long source){
	    return lt(source, ZERO);
	}
	public static boolean lt1(Long source){
	    return lt(source, ONE);
	}
	public static boolean lte0(Long source){
	    return lte(source, ZERO);
	}
	public static boolean lte1(Long source){
	    return lte(source, ONE);
	}
	
}
