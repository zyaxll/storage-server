package com.b5m.core.dao.spring;

import com.b5m.core.dao.helper.MapperHelper;
import com.b5m.core.dao.mapper.Marker;
import com.b5m.utils.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.Properties;

/**
 * @description: 自定义Mapper扫描配置, 并完成Mapper的初始化
 *
 * 配置方式如下:
 * <bean class="com.b5m.core.dao.spring.MapperScannerConfigurer">
 *      <property name="basePackage" value="com.b5m.storage.dao"/>
 * </bean>
 * 采用该配置时, 不需要使用Mybatis的扫描注册方式.
 *
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-4-1
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-4-1       Leo.li          1.0             TODO
 */
public class MapperScannerConfigurer extends org.mybatis.spring.mapper.MapperScannerConfigurer {

    private MapperHelper mapperHelper = new MapperHelper();

    public void setMarkerInterface(Class<?> superClass) {
        super.setMarkerInterface(superClass);
        if (Marker.class.isAssignableFrom(superClass)) {
            mapperHelper.registerMapper(superClass);
        }
    }

    public MapperHelper getMapperHelper() {
        return mapperHelper;
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    /**
     * 属性注入
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
        mapperHelper.setProperties(properties);
    }

    /**
     * 注册完成后，对MapperFactoryBean的类进行特殊处理
     *
     * @param registry
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        super.postProcessBeanDefinitionRegistry(registry);
        //如果没有注册过接口，就注册默认的Mapper接口
        this.mapperHelper.ifEmptyRegisterDefaultInterface();
        String[] names = registry.getBeanDefinitionNames();
        GenericBeanDefinition definition;
        for (String name : names) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(name);
            if (beanDefinition instanceof GenericBeanDefinition) {
                definition = (GenericBeanDefinition) beanDefinition;
                if (StringUtils.isNotEmpty(definition.getBeanClassName())
                        && definition.getBeanClassName().equals("org.mybatis.spring.mapper.MapperFactoryBean")) {
                    definition.setBeanClass(MapperFactoryBean.class);
                    definition.getPropertyValues().add("mapperHelper", this.mapperHelper);
                }
            }
        }
    }
}