package com.b5m.core.dao.helper;

import com.b5m.core.dao.provider.core.BaseProvider;
import com.b5m.utils.Assert;
import com.b5m.utils.StringUtils;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.util.*;

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

    private List<Class<?>> registerClass = new ArrayList<>();

    /**
     * 缓存msid和Provider
     */
    private Map<String, BaseProvider> msIdCache = new HashMap<>();

    /**
     * 缓存skip结果
     */
    private final Map<String, Boolean> msIdSkip = new HashMap<>();

    /**
     * 通用Mapper配置
     */
//    private Config config = new Config();


    public MapperHelper() {
    }

    public MapperHelper(Properties properties) {
        this();

    }

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
        if (!registerMapper.containsKey(mapperClass)) {
            registerClass.add(mapperClass);
            registerMapper.put(mapperClass, fromMapperClass(mapperClass));
        }

        //自动注册继承的接口
        Class<?>[] interfaces = mapperClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> anInterface : interfaces) {
                registerMapper(anInterface);
            }
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
     * 判断接口是否包含通用接口
     *
     * @param mapperInterface
     * @return
     */
    public boolean isExtendCommonMapper(Class<?> mapperInterface) {
        for (Class<?> mapperClass : registerClass) {
            if (mapperClass.isAssignableFrom(mapperInterface)) {
                return true;
            }
        }
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

    /**
     * 如果当前注册的接口为空，自动注册默认接口
     */
    public void ifEmptyRegisterDefaultInterface() {
        if (registerClass.size() == 0) {
            registerMapper("com.b5m.core.dao.mapper.CommonMapper");
        }
    }

    /**
     * 配置完成后，执行下面的操作
     * <br>处理configuration中全部的MappedStatement
     *
     * @param configuration
     */
    public void processConfiguration(Configuration configuration) {
        processConfiguration(configuration, null);
    }

    /**
     * 配置指定的接口
     *
     * @param configuration
     * @param mapperInterface
     */
    public void processConfiguration(Configuration configuration, Class<?> mapperInterface) {
        String prefix;
        if (mapperInterface != null) {
            prefix = mapperInterface.getCanonicalName();
        } else {
            prefix = "";
        }
        for (Object object : new ArrayList<Object>(configuration.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                if (ms.getId().startsWith(prefix) && isMapperMethod(ms.getId())) {
                    if (ms.getSqlSource() instanceof ProviderSqlSource) {
                        setSqlSource(ms);
                    }
                }
            }
        }
    }


    /**
     * 配置属性
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
//        config.setProperties(properties);
        //注册通用接口
        String mapper = null;
        if (properties != null) {
            mapper = properties.getProperty("mappers");
        }
        if (StringUtils.isNotEmpty(mapper)) {
            String[] mappers = mapper.split(",");
            for (String mapperClass : mappers) {
                if (mapperClass.length() > 0) {
                    registerMapper(mapperClass);
                }
            }
        }
    }

}
