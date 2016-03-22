package com.b5m.storage.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 * @author lucky.liu
 * @version v2.0.0 2014年9月4日 下午4:44:19
 */
public abstract class ReflectionUtils {

    private ReflectionUtils(){}

    /**
     * 获取父类泛型对应的实际类型
     * @param clz 
     * @return
     */
    public static Map<Type, Type> getSuperGenericMap(Class<?> clz){
        return getGenericMap(clz, clz.getSuperclass());
    }
    
    /**
     * 获取祖先类泛型参数对应的实际类型
     * @param children 子类，一般不带泛型参数
     * @param parent 祖先类
     * @return 
     */
    public static Map<Type, Type> getGenericMap(Class<?> children, Class<?> parent){
        Type[] actualTypes = getActualType(children, parent);
        Map<Type, Type> map = new HashMap<Type, Type>();
        if(actualTypes == null){
            return map;
        }
        Type[] types = parent.getTypeParameters();
        if(types == null || types.length != actualTypes.length){
            return map;
        }
        for(int i = 0; i < types.length; ++i){
            map.put(types[i], actualTypes[i]);
        }
        return map;
    }
    
    /**
     * 获取祖先类实际类型
     * @param children 子类，一般不带泛型参数
     * @param parent 祖先类
     * @return 
     */
    private static Type[] getActualType(Class<?> children, Class<?> parent){
        if(children == null || parent == null){
            return null;
        }else if(!parent.isAssignableFrom(children)){
            return null;
        }
        if(children.equals(parent)){
            Type[] types = children.getTypeParameters();
            return types;
        }
        
        Class<?> clz = children;
        Type[] realTypes = {};
        Map<Type, Type> map = new HashMap<Type, Type>();
        do{
            Type[] types = clz.getTypeParameters();
            for(int i = 0; i < types.length; ++i){
                map.put(types[i], realTypes[i]);
            }
            
            Type type = clz.getName().matches(".*\\$\\$EnhancerBy.*CGLIB\\$\\$.*") ?
                    clz.getSuperclass().getGenericSuperclass() : clz.getGenericSuperclass();
            
            if(type instanceof ParameterizedType){
                Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                if(realTypes.length < params.length){
                    realTypes = new Type[params.length];
                }
                
                for(int i = 0; i < params.length; ++i){
                    Type t = params[i];
                    if(t instanceof Class){
                        realTypes[i] = t;
                    }else{
                        realTypes[i] = map.get(params[i]);
                    }
                }
                map.clear();
            }else if(type instanceof Class && ((Class<?>)type).isAssignableFrom(clz.getSuperclass())){
                
            }else{
                System.out.println("type is " + type + ", super class is " + clz.getSuperclass() + ", class is " + clz.getSimpleName() + "'s superclass not ParameterizedType");
                return null;
            }
            
            clz = clz.getSuperclass();
        } while(!clz.equals(parent));
        return realTypes;
    }
    
    /**
     * 获取父类泛型参数
     * @param children
     * @param parent
     * @param parentGenericIndex
     * @return 返回对应泛型参数的类型
     */
    public static Type getGenericType(Class<?> children, Class<?> parent, int parentGenericIndex){
        Type[] realTypes = getActualType(children, parent);
        return parentGenericIndex < (realTypes == null ? 0 : realTypes.length) ? realTypes[parentGenericIndex] : Object.class;
    }
    
    /**
     * 通过反射,获得Class定义中声明的父类的第一个泛型参数类型
     * @see {@link ReflectionUtils#getSuperClassGenricType(Class, int)}
     * @param clazz
     * @return 如无法找到, 返回null
     */
    public static Class<?> getSuperClassGenricType(final Class<?> clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得Class定义中声明的父类的泛型参数的类型.
     * @param clazz 
     * @param index 泛型参数位置，从左到右由0开始
     * @return 如无法找到, 返回null
     */
    public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {
        Type type = getGenericType(clazz, clazz.getSuperclass(), index);
        if(type instanceof Class){
            return (Class<?>)type;
        }else{
            return null;
        }
    }
    
    /**
     * 将反射相关异常转换为运行时异常
     * @param e
     * @return
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
            || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.",
                ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }
    
    private static boolean equals(Class<?> a, Class<?> b){
    	return a == b;
    }
    
    /**
     * 实现基本数据类型、日期类型、封装类型间的智能转换
     * @param target 转换类型
     * @param srcValue 原始数据
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T convertType(Class<T> target, Object srcValue){
        if(srcValue == null){
            return null;
        }
        if(target == null){
            throw new NullPointerException();
        }
        Class<?> src = srcValue.getClass();
        if(target.equals(src)){
            return (T)srcValue;
        }
        
        Object obj;
        try{
            if (equals(target, boolean.class) || equals(target, Boolean.class)) {
                obj = BooleanUtils.valueOf(srcValue);
            } else if (equals(target, byte.class) || equals(target, Byte.class)) {
                obj = ByteUtils.valueOf(srcValue);
            } else if (equals(target, short.class) || equals(target, Short.class)) {
                obj = ShortUtils.valueOf(srcValue);
            } else if (equals(target, int.class) || equals(target, Integer.class)) {
                obj = IntUtils.valueOf(srcValue);
            } else if (equals(target, long.class) || equals(target, Long.class)) {
                obj = LongUtils.valueOf(srcValue);
            } else if (equals(target, float.class) || equals(target, Float.class)) {
                obj = FloatUtils.valueOf(srcValue);
            } else if (equals(target, double.class) || equals(target, Double.class)) {
                obj = DoubleUtils.valueOf(srcValue);
            } else if (equals(target, char.class) || equals(target, Character.class)) {
                obj = CharUtils.valueOf(srcValue);
            } else if (equals(target, BigInteger.class)) {
                obj = BigIntegerUtils.valueOf(srcValue);
            } else if(equals(target, BigDecimal.class)) {
                obj = BigDecimalUtils.valueOf(srcValue);
            } else if (equals(target, Date.class)) {
                obj = DateUtils.valueOf(srcValue);
            } else if(target.isEnum()){
                obj = Enum.valueOf((Class<Enum>)target, srcValue.toString());
            } else if(equals(target, String.class)){
                obj = srcValue.toString();
            }else{
                throw new RuntimeException(target+"不支持自动转换");
            }
            return (T)obj;
        }catch(Exception e){
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 通过反射给指定类实例字段注入数据<br>
     * 数据类型将会自动实现转换<br>
     * 如果字段类型为日期类型，会自动将"yyyy-MM-dd HH:mm:ss"格式的字符串转换为日期类型
     * @param target 类实例
     * @param field 对应字段
     * @param value 字段数值
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public static void setFieldValueAuto(Object target, Field field, Object value) {
		if (target == null || value == null) {
			return;
		}
		Class<?> clz = field.getType();
		try{
		    if(equals(clz, value.getClass())){
		        field.set(target, value);
		        return;
		    }
		    if (equals(clz, boolean.class)) {
		        field.setBoolean(target, BooleanUtils.parse(value));
            } else if (equals(clz, Boolean.class)) {
                field.set(target, BooleanUtils.valueOf(value));
            } else if (equals(clz, byte.class)) {
                field.setByte(target, ByteUtils.parse(value));
            } else if (equals(clz, Byte.class)) {
                field.set(target, ByteUtils.valueOf(value));
            } else if (equals(clz, short.class)) {
                field.setShort(target, ShortUtils.parse(value));
            } else if (equals(clz, Short.class)) {
                field.set(target, ShortUtils.valueOf(value));
            } else if (equals(clz, int.class)) {
                field.setInt(target, IntUtils.parse(value));
            } else if (equals(clz, Integer.class)) {
                field.set(target, IntUtils.valueOf(value));
            } else if (equals(clz, long.class)) {
                field.setLong(target, LongUtils.parse(value));
            } else if (equals(clz, Long.class)) {
                field.set(target, LongUtils.valueOf(value));
            } else if (equals(clz, float.class)) {
                field.setFloat(target, FloatUtils.parse(value));
            } else if (equals(clz, Float.class)) {
                field.set(target, FloatUtils.valueOf(value));
            } else if (equals(clz, double.class)) {
                field.setDouble(target, DoubleUtils.parse(value));
            } else if (equals(clz, Double.class)) {
                field.set(target, DoubleUtils.valueOf(value));
            } else if (equals(clz, char.class)) {
                field.setChar(target, CharUtils.parse(value));
            } else if (equals(clz, Character.class)) {
                field.set(target, CharUtils.valueOf(value));
            } else if (equals(clz, BigInteger.class)) {
                field.set(target, BigIntegerUtils.valueOf(value));
            } else if (equals(clz, BigDecimal.class)) {
                field.set(target, BigDecimalUtils.valueOf(value));
            } else if (equals(clz, Date.class)) {
                field.set(target, DateUtils.valueOf(value));
			} else if(clz.isEnum()){
			    field.set(target, Enum.valueOf((Class<Enum>)clz, value.toString()));
			} else{
				field.set(target, value);
			}
		}catch(Exception e){
			throw convertReflectionExceptionToUnchecked(e);
		}
	}
	
	/**
	 * 给指定实例注入字段数据,类型自动转换
	 * @param obj 类实例
	 * @param field 字段名称
	 * @param value 字段数值
	 */
	public static void setValueAuto(Object obj, String field, Object value){
	    Class<?> p =obj.getClass();
	    while(!Object.class.equals(p)){
            try {
                Field f = p.getDeclaredField(field);
                f.setAccessible(true);
                setFieldValueAuto(obj, f, value);
            } catch (Exception e) {
//                throw new RuntimeException(e);
            }
            p = p.getSuperclass();
	    }
	}
	
	/**
	 * 给指定实例注入字段数据,类型自动转换
	 * @param obj 类实例
	 * @param field 字段名称
	 * @param value 字段数值
	 */
	public static void setFieldValue(Object obj, String field, Object value){
	    Class<?> p =obj.getClass();
	    while(!Object.class.equals(p)){
	        try {
	            Field f = p.getDeclaredField(field);
	            f.setAccessible(true);
	            f.set(obj, value);
	        } catch (Exception e) {
//                throw new RuntimeException(e);
	        }
	        p = p.getSuperclass();
	    }
	}
	
	/**
     * 设置字段数值
     * 
     * @param fieldName
     * @param value
     * @param ignoreNull
     */
    public static void setFieldValue(Object bean, String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && value == null) {
            return;
        }
        setFieldValue(bean, fieldName, value);
    }
	
	/**
     * 复制父类共有属性，要求两个类的共同父类对应的泛型参数类型一致
     * @param source 源对象实例
     * @param target 目标类实例
     * @param superClass 公共父类
     */
    public static void clone(Object source,Object target, Class<?> superClass) {
        if(superClass == null){
            return;
        }
        while(!Object.class.equals(superClass)){
            try {
                Field[] fields = superClass.getDeclaredFields();
                for(Field field : fields){
                    int modifier = field.getModifiers();
                    //静态和final类型不需要注入
                    if(!Modifier.isStatic(modifier) && !Modifier.isFinal(modifier)){
                        field.setAccessible(true);
                        field.set(target, field.get(source));
                    }
                }
                superClass = superClass.getSuperclass();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
	
	/**
     * 通过父类实例创建子类实例，子类实例将继承父类实例的数据
     * @param parent 父类实例
     * @param childClass 子类class
     * @return 返回子类实例
     */
    public static <P,C extends P> C cloneParent(P parent,Class<C> childClass) {
        if(childClass == null){
            throw new NullPointerException();
        }
        try{
            C child = childClass.newInstance();
            if(parent != null){
                clone(parent, child, parent.getClass());
            }
            return child;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 通过子类实例创建父类实例，父类将拥有与子类共有的数据
     * @param child 父类实例
     * @param parentClass 子类class
     * @return 返回子类实例
     */
    public static <P,C extends P> P cloneChild(C child,Class<P> parentClass) {
        if(parentClass == null){
            throw new NullPointerException();
        }
        try{
            P parent = parentClass.newInstance();
            if(child != null){
                clone(child, parent, parentClass);
            }
            return parent;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 浅拷贝，只拷贝该实例的属性，不会递归拷贝
     * @param source 数据源
     * @return 新对象实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T source){
        Class<T> clz = (Class<T>) source.getClass();
        try {
            T target = clz.getConstructor(new Class[0]).newInstance(new Object[0]);
            clone(source, target, clz);
            return target;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 将源实例的非空字段数据拷贝到目标实例中
     * @param src 源实例
     * @param target 目标实例
     * @return
     */
    public static <T> T copyIgnoreNull(T src, T target){
        return copyIgnoreNull(src, target, true);
    }

    /**
     * 将源对象的数据拷贝到目标对象中
     * @param src 源对象实例
     * @param target 目标对象实例
     * @param ignoreNull 是否忽略空数据
     * @return 返回拷贝后的对象实例
     */
    public static <T> T copyIgnoreNull(T src, T target, boolean ignoreNull){
        if(src == null){
            return target;
        }else if(target == null){
            return src;
        }
        
        Class<?> clz = src.getClass();
        while(!Object.class.equals(clz)){
            try {
                Field[] fields = clz.getDeclaredFields();
                for(Field field : fields){
                    int modifier = field.getModifiers();
                    //静态和final类型不需要注入
                    if(!Modifier.isStatic(modifier) && !Modifier.isFinal(modifier)){
                        field.setAccessible(true);
                        Object value = field.get(src);
                        if(ignoreNull && value == null){
                            continue;
                        }
                        field.set(target, value);
                    }
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            
            clz = clz.getSuperclass();
        }
        
        return target;
    }
    
    /**
     * 将类属性转换为Map<br>
     * <strong>注意：如果父类有同名字段，将被覆盖</strong>
     * @param clz 
     * @return
     */
    public static Map<String, Field> getFieldMap(Class<?> clz){
        Map<String, Field> map = new HashMap<String, Field>();
        while(!Object.class.equals(clz)){
            Field[] fields = clz.getDeclaredFields();
            for(Field f : fields){
                if(Modifier.isStatic(f.getModifiers())){
                   continue; 
                }
                map.put(f.getName(), f);
            }
            clz = clz.getSuperclass();
        }
        return map;
    }
    
    /**
     * 将类属性转换为Map<br>
     * <strong>注意：如果父类有同名字段，将被覆盖</strong>
     * @param clz 
     * @return
     */
    public static Map<String, Field> getAccessibleFieldMap(Class<?> clz){
        Map<String, Field> map = new HashMap<String, Field>();
        while(!Object.class.equals(clz)){
            Field[] fields = clz.getDeclaredFields();
            for(Field f : fields){
                if(Modifier.isStatic(f.getModifiers())){
                    continue; 
                }
                f.setAccessible(true);
                map.put(f.getName(), f);
            }
            clz = clz.getSuperclass();
        }
        return map;
    }

    /**
     * 获取对应字段的数值
     * @param obj 类实例
     * @param field 字段名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object obj, String field) {
        Class<?> clz = obj.getClass();
        while(!Object.class.equals(clz)){
            try{
                Field f = clz.getDeclaredField(field);
                if(f != null){
                    f.setAccessible(true);
                    Object value = f.get(obj);
                    return (T)value;
                }
            }catch(Exception e){
                //e.printStackTrace();
            }
            clz = clz.getSuperclass();
        }
        return null;
    }
    
    private static void close(Closeable c){
        if(c != null){
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
    
    /**
     * 深度拷贝
     * @param obj 拷贝对象实例
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T obj){
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();
            
            //分配内存，写入原始对象，生成新对象
            bis = new ByteArrayInputStream(out.toByteArray());
            ois = new ObjectInputStream(bis);
            //返回生成的新对象
            return (T) ois.readObject();
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }finally{
            close(bos);
            close(oos);
            close(bis);
            close(ois);
        }
    }
    
    /**
     * 根对象类型构造生成实例
     * @param clz 对象类型
     * @return
     */
    public static <T> T create(Class<T> clz){
        try {
            T obj = clz.getConstructor(new Class[0]).newInstance(new Object[0]);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * 将map转换成指定对象类型的实例
     * @param clazz 对象类型
     * @param map 
     * @return
     */
    public static <T> T getObject(Class<T> clazz, Map<?, ?> map){
        T obj = create(clazz);
        Class<?> clz = clazz;
        while(!Object.class.equals(clz)){
            Field[] fields = clz.getDeclaredFields();
            for(Field f : fields){
                int modifier = f.getModifiers();
                if(!Modifier.isFinal(modifier) || Modifier.isStatic(modifier)){
                    f.setAccessible(true);
                    String name = f.getName();
                    Object value = map.get(name);
                    try {
                        f.set(obj, value);
                    } catch (Exception e) {
                    }
                }
            }
            clz = clz.getSuperclass();
        }
        
        return obj;
    }
    
    /**
     * 通过给定的正则式，调用指定对象中名称匹配的公有无参方法，且不包括静态方法
     * @param obj
     * @param methodRegex
     */
    public static <T> void invokeMethod(T obj, String methodRegex){
        if(obj == null){
            return;
        }
        Class<?> clz = obj.getClass();
        do{
            Method[] methods = clz.getDeclaredMethods();
            for(Method method : methods){
                int modifier = method.getModifiers();
                if(Modifier.isStatic(modifier) || !Modifier.isPublic(modifier)){
                    continue;
                }
                if(!method.getName().matches(methodRegex)){
                    continue;
                }
                int len = method.getParameterTypes().length;
                if(len < 1){
                    try {
                        method.invoke(obj, new Object[0]);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            }
            clz = clz.getSuperclass();
        }while(!Object.class.equals(clz));
    }
    
    /**
     * 获取字段的实际参数类型
     * @param entityClass 实体对象类型，即不带泛型参数的子类
     * @param delaredClass 字段所在对象类型
     * @param f 字段
     * @return
     */
    public static Class<?> getFieldType(Class<?> entityClass, Class<?> delaredClass, Field f){
        Type type = f.getGenericType();
        if(type instanceof Class){
            return f.getType();
        }else if(type instanceof GenericArrayType){
            return f.getType();
        }else{
            Map<Type, Type> typeMap = getGenericMap(entityClass, delaredClass);
            return (Class<?>)typeMap.get(type);
        }
    }
    
    /**
     * 获取字段的参数类型
     * @param delaredClass 字段所在对象类型
     * @param f 字段
     * @return
     */
    public static Class<?> getFieldType(Class<?> delaredClass, String field){
        Class<?> p = delaredClass;
        while(!Object.class.equals(p)){
            try {
                Field f = p.getDeclaredField(field);
                if(f != null){
                    return getFieldType(delaredClass, p, f);
                }
            } catch (NoSuchFieldException e) {
            }
            p = p.getSuperclass();
        }
        throw new RuntimeException(delaredClass.getName() + "无字段" + field);
    }
    
    /**
     * 根据实例字段调用实例的get方法
     * @param bean
     * @param field
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getter(Object bean, Field field) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        String getMethod = "get" + StringUtils.capitalize(field.getName());
        Class<?> declaringClz = field.getDeclaringClass();
        Method method = declaringClz.getDeclaredMethod(getMethod);
        return (T) method.invoke(bean);
    }
    
    /**
     * @deprecated at v0.2.0 {@link #getterOrGet(Object, String)}
     */
    @Deprecated
    public static <T> T get(Object bean, String fieldName) {
        return getterOrGet(bean, fieldName);
    }
    
    /**
     * 获取实体字段的数据，优先返回子类对应数值<br>
     * <p>
     * 存在getter方法则优先返回getter方法对应的数值, 如果getter方法在子类中实例，将无法获取到getter方法的返回值
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getterOrGet(Object bean, String fieldName) {
        if(bean == null){
            return null;
        }
        Class<?> clz = bean.getClass();
        while (!Object.class.equals(clz)) {
            try {
                Field f = clz.getDeclaredField(fieldName);
                if (f != null && !Modifier.isStatic(f.getModifiers())
                    && !Modifier.isFinal(f.getModifiers())) {
                    try {
                        return getter(bean, f);
                    } catch (Throwable t) {
                        f.setAccessible(true);
                        return (T) f.get(bean);
                    }
                }
            } catch (Throwable e) {
            }
            clz = clz.getSuperclass();
        }
        return null;
    }
    
    /**
     * 获取字段对应的getter方法返回值
     * <p>
     * 存在bug, 如果getter方法在子类中实例，将无法获取到getter方法的返回值
     * @param fieldName
     * @return
     */
    public static <T> T getter(Object bean, String fieldName) {
        if(bean == null){
            return null;
        }
        Class<?> clz = bean.getClass();
        while (!Object.class.equals(clz)) {
            try {
                Field f = clz.getDeclaredField(fieldName);
                if (f != null && !Modifier.isStatic(f.getModifiers())
                    && !Modifier.isFinal(f.getModifiers())) {
                    try {
                        return getter(bean, f);
                    } catch (Throwable t) {
                    }
                }
            } catch (Throwable e) {
            }
            clz = clz.getSuperclass();
        }
        return null;
    }

    /**
     * @deprecated at v0.2.0 {@link #setterOrSet(Object, String, Object)}
     */
    @Deprecated
    public static void set(Object bean, String fieldName, Object value) {
        setterOrSet(bean, fieldName, value, false);
    }
    
    /**
     * 设置字段数值，忽略空值
     * 
     * @see #set(String, Object, boolean)
     * @param fieldName
     * @param value
     */
    public static void setterOrSet(Object bean, String fieldName, Object value) {
        setterOrSet(bean, fieldName, value, false);
    }

    /**
     * @deprecated at v0.2.0 {@link #setterOrSetIgNull(Object, String, Object)}
     */
    @Deprecated
    public static void setIgnoreNull(Object bean, String fieldName, Object value) {
        setterOrSet(bean, fieldName, value, true);
    }
    
    /**
     * 设置字段数值，忽略空值
     * 
     * @see #set(String, Object, boolean)
     * @param fieldName
     * @param value
     */
    public static void setterOrSetIgNull(Object bean, String fieldName, Object value) {
        setterOrSet(bean, fieldName, value, true);
    }

    /**
     * 设置字段对应的数值，优化使用子类字段或setter方法<br>
     * 如果setter方法存在，则仅使用setter方法
     * 
     * @param fieldName 字段名
     * @param value 字段值
     * @param ignoreNull 是否忽略<code>null</code>值
     * @deprecated at v0.2.0 {@link #setterOrSet(Object, String, Object, boolean)}
     */
    @Deprecated
    public static void set(Object bean, String fieldName, Object value, boolean ignoreNull) {
        if(bean == null){
            return;
        }
        if (ignoreNull && value == null) {
            return;
        }
        Class<?> clz = bean.getClass();
        while (!Object.class.equals(clz)) {
            try {
                Field f = clz.getDeclaredField(fieldName);
                if (f != null && !Modifier.isStatic(f.getModifiers())
                    && !Modifier.isFinal(f.getModifiers())) {
                    try {
                        setter(bean, f, value);
                    } catch (Throwable e) {
                        f.setAccessible(true);
                        f.set(bean, value);
                    }
                    return;
                }
            } catch (Throwable e) {
            }
            clz = clz.getSuperclass();
        }
    }
    
    /**
     * 设置字段对应的数值，优化使用子类字段或setter方法<br>
     * 如果setter方法存在，则仅使用setter方法
     * 
     * @param fieldName 字段名
     * @param value 字段值
     * @param ignoreNull 是否忽略<code>null</code>值
     */
    public static void setterOrSet(Object bean, String fieldName, Object value, boolean ignoreNull) {
        if(bean == null){
            return;
        }
        if (ignoreNull && value == null) {
            return;
        }
        Class<?> clz = bean.getClass();
        while (!Object.class.equals(clz)) {
            try {
                Field f = clz.getDeclaredField(fieldName);
                if (f != null && !Modifier.isStatic(f.getModifiers())
                    && !Modifier.isFinal(f.getModifiers())) {
                    try {
                        setter(bean, f, value);
                    } catch (Throwable e) {
                        f.setAccessible(true);
                        f.set(bean, value);
                    }
                    return;
                }
            } catch (Throwable e) {
            }
            clz = clz.getSuperclass();
        }
    }
    
    /**
     * @see #setterOrSetAuto(Object, Field, Object, boolean)
     * @param fieldName 字段名
     * @param value 字段值
     */
    public static void setterOrSetAuto(Object bean, Field field, Object value) {
        setterOrSetAuto(bean, field, value, false);
    }
    
    /**
     * 设置字段对应的数值，优化使用子类字段或setter方法<br>
     * 如果setter方法存在，则仅使用setter方法
     * 
     * @param fieldName 字段名
     * @param value 字段值
     * @param ignoreNull 是否忽略<code>null</code>值
     */
    public static void setterOrSetAuto(Object bean, Field field, Object value, boolean ignoreNull) {
        if(bean == null){
            return;
        }
        if (ignoreNull && value == null) {
            return;
        }
        try {
            setterAuto(bean, field, value);
        } catch (Throwable e) {
            field.setAccessible(true);
            setFieldValueAuto(bean, field, value);
        }
    }

    /**
     * 根据实例字段，调用实例的setter方法
     * @param bean 对象实例
     * @param field 字段
     * @param value 字段数值
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setterAuto(Object bean, Field field, Object value) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        String setMethod = "set" + StringUtils.capitalize(field.getName());
        Class<?> declaringClz = field.getDeclaringClass();
        Class<?> paramClz = getFieldType(bean.getClass(), declaringClz, field);
        Method method = declaringClz.getDeclaredMethod(setMethod, paramClz);
        if(value != null && !equals(paramClz, value.getClass())){
            value = convertType(paramClz, value);
        }
        method.invoke(bean, value);
    }
    
    
    /**
     * 根据实例字段，调用实例的setter方法
     * @param bean 对象实例
     * @param field 字段
     * @param value 字段数值
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setter(Object bean, Field field, Object value) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        String setMethod = "set" + StringUtils.capitalize(field.getName());
        Class<?> declaringClz = field.getDeclaringClass();
        Class<?> paramClz = getFieldType(bean.getClass(), declaringClz, field);
        Method method = declaringClz.getDeclaredMethod(setMethod, paramClz);
        method.invoke(bean, value);
    }
    
    /**
     * 调用字段对应的setter方法
     * 
     * @see #setter(String, Object, boolean)
     * @param fieldName 字段名称
     * @param value 字段数值
     */
    public static void setter(Object obj, String fieldName, Object value) {
        setter(obj, fieldName, value, false);
    }

    /**
     * 调用字段对应的setter方法
     * <p>
     * 存在bug,如果setter方法在子类中定义，将无法获取调用到
     * 
     * @param fieldName 字段名称
     * @param value 字段数值
     * @param ignoreNull 是否忽略空值
     */
    public static void setter(Object obj, String fieldName, Object value, boolean ignoreNull) {
        if(obj == null || (ignoreNull && value == null)){
            return;
        }
        Class<?> clz = obj.getClass();
        while (!Object.class.equals(clz)) {
            try {
                Field f = clz.getDeclaredField(fieldName);
                if (f != null && !Modifier.isStatic(f.getModifiers())
                    && !Modifier.isFinal(f.getModifiers())) {
                    try {
                        setter(obj, f, value);
                        return;
                    } catch (Throwable e) {
                    }
                }
            } catch (Throwable e) {
            }
            clz = clz.getSuperclass();
        }
    }

    
    /**
     * 通过反射调实例的方法
     * @param obj 对象实例
     * @param methodName 方法名
     * @param argTypes 方法参数类型
     * @param args 方法参数
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T invokeMethod(Object obj, String methodName, Class<?>[] argTypes, Object[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        return invokeMethod(obj, obj.getClass(), methodName, argTypes, args);
    }
    
    /**
     * 通过反射调用实例父类的方法
     * @param obj 对象实例
     * @param parentClz 父对象
     * @param methodName 父对象对应的方法名
     * @param argTypes 方法参数类型
     * @param args 方法参数
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object obj, Class<?> parentClz, String methodName, Class<?>[] argTypes, Object[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Method method = parentClz.getDeclaredMethod(methodName, argTypes);
        method.setAccessible(true);
        return (T)method.invoke(obj, args);
    }
    
    
}
