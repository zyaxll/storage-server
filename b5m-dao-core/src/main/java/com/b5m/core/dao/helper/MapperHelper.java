package com.b5m.core.dao.helper;

import com.b5m.core.dao.provider.core.BaseProvider;
import com.b5m.utils.Assert;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-29
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-29       Leo.li          1.0             TODO
 */
public class MapperHelper {

    /**
     * 注册的通用Mapper接口
     */
    private Map<Class<?>, BaseProvider> registerMapper = new HashMap<>();

    /**
     * 缓存msid和Provider
     */
    private Map<String, BaseProvider> msIdCache = new HashMap<>();

    /**
     * 缓存skip结果
     */
    private final Map<String, Boolean> msIdSkip = new HashMap<>();

    /**
     * 通过通用Mapper接口获取对应的BaseProvider
     *
     * @param mapperClass
     * @return MapperTemplate
     */
    private BaseProvider fromMapperClass(Class<?> mapperClass) {
        Method[] methods = mapperClass.getDeclaredMethods();

        if (methods.length == 0) {
            return null;
        }

        Class<?> templateClass = null;
        Class<?> tempClass = null;
        Set<String> methodSet = new HashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SelectProvider.class)) {
                SelectProvider provider = method.getAnnotation(SelectProvider.class);
                tempClass = provider.type();
                methodSet.add(getProviderMethodName(provider.method(), method));
            } else if (method.isAnnotationPresent(InsertProvider.class)) {
                InsertProvider provider = method.getAnnotation(InsertProvider.class);
                tempClass = provider.type();
                methodSet.add(getProviderMethodName(provider.method(), method));
            } else if (method.isAnnotationPresent(DeleteProvider.class)) {
                DeleteProvider provider = method.getAnnotation(DeleteProvider.class);
                tempClass = provider.type();
                methodSet.add(getProviderMethodName(provider.method(), method));
            } else if (method.isAnnotationPresent(UpdateProvider.class)) {
                UpdateProvider provider = method.getAnnotation(UpdateProvider.class);
                tempClass = provider.type();
                methodSet.add(getProviderMethodName(provider.method(), method));
            }
            if (templateClass == null) {
                templateClass = tempClass;
            } else if (templateClass != tempClass) {
                throw new RuntimeException("一个通用Mapper中只允许存在一个BaseProvider子类!");
            }
        }
        if (templateClass == null || !BaseProvider.class.isAssignableFrom(templateClass)) {
            throw new RuntimeException("接口中不存在包含type为BaseProvider的Provider注解，这不是一个合法的通用Mapper接口类!");
        }

        BaseProvider provider;
        try {
            provider = (BaseProvider) templateClass.getConstructor(Class.class, MapperHelper.class).newInstance(mapperClass, this);
        } catch (Exception e) {
            throw new RuntimeException("实例化BaseProvider对象失败:" + e.getMessage());
        }

        //注册方法
        for (String methodName : methodSet) {
            try {
                provider.addMethod(methodName, templateClass.getMethod(methodName, MappedStatement.class));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(templateClass.getCanonicalName() + "中缺少" + methodName + "方法!");
            }
        }

        return provider;
    }

    private List<Method> getMethod(Class<?> clazz, List<Method> methods) {

        if (null == methods) {
            methods = new ArrayList<>();
        }

        Method[] selfMethods = clazz.getDeclaredMethods();
        if (selfMethods.length > 0) {
            methods.addAll(Arrays.asList(selfMethods));
        }

        Class<?>[] parentInterface = clazz.getInterfaces();
        for (Class<?> parentClass : parentInterface) {
            methods.addAll(Arrays.asList(parentClass.getDeclaredMethods()));
        }

        return methods;
    }

    private String getProviderMethodName(String providerName, Method method) {
        if (providerName.equals("dynamicSQL")) {
            providerName = method.getName();
        }

        return providerName;
    }

    /**
     * 注册通用Mapper接口
     *
     * @param mapperClass
     * @throws RuntimeException
     */
    public void registerMapper(Class<?> mapperClass) {
        if (registerMapper.get(mapperClass) == null) {

            registerMapper.put(mapperClass, fromMapperClass(mapperClass));

            Class<?>[] parentClasses = mapperClass.getInterfaces();
            for (Class<?> parentClass : parentClasses) {
                registerMapper.put(parentClass, fromMapperClass(parentClass));
            }
//            EntityHelper.initEntityNameMap(mapperClass);
        } else {
            throw new RuntimeException("已经注册过的通用Mapper[" + mapperClass.getCanonicalName() + "]不能多次注册!");
        }
    }

    /**
     * 注册通用Mapper接口
     * 注册通用Mapper入口
     *
     * @param mapperClass
     * @throws RuntimeException
     */
    public void registerMapper(String mapperClass) {
        try {
            registerMapper(Class.forName(mapperClass));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("注册通用Mapper[" + mapperClass + "]失败，找不到该通用Mapper!");
        }
    }

    /**
     * 判断当前的接口方法是否需要进行拦截
     *
     * @param msId
     * @return
     */
    public boolean isMapperMethod(String msId) {
        if (msIdSkip.get(msId) != null) {
            return msIdSkip.get(msId);
        }

        for (Map.Entry<Class<?>, BaseProvider> entry : registerMapper.entrySet()) {
            if (null != entry.getValue() && entry.getValue().supportMethod(msId)) {
                msIdSkip.put(msId, true);
                return true;
            }
        }

        msIdSkip.put(msId, false);

        return false;
    }

    /**
     * 获取MapperTemplate
     *
     * @param msId
     * @return
     */
    private BaseProvider getMapperProvider(String msId) {
        BaseProvider provider = msIdCache.get(msId);
        if (null == provider) {
            for (Map.Entry<Class<?>, BaseProvider> entry : registerMapper.entrySet()) {
                if (null != entry.getValue() && entry.getValue().supportMethod(msId)) {
                    provider = entry.getValue();
                    msIdCache.put(msId, provider);
                    break;
                }
            }
        }

        Assert.notNull(provider, "provider不存在{msId=" + msId + "}");

        return provider;
    }

    /**
     * 重新设置SqlSource
     *
     * @param ms
     */
    public void setSqlSource(MappedStatement ms) {
        BaseProvider provider = getMapperProvider(ms.getId());
        try {
            provider.setSqlSource(ms);
        } catch (Exception e) {
            throw new RuntimeException("调用方法异常:" + e.getMessage());
        }
    }

}
