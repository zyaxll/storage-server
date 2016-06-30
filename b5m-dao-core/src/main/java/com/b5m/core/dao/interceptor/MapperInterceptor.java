package com.b5m.core.dao.interceptor;


import com.b5m.core.dao.helper.MapperHelper;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @description: mybatis 通用Mapper拦截器
 *
 * <p>
 *     主要用于拦截Mapper执行方法, 并完成sqlSource的注册, 具体配置方式如下:
 *     在注册的sqlSessionFactory增加以下配置
 *     <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
 *          <property name="dataSource" ref="dataSource"/>
 *          <property name="mapperLocations">
 *              <list>
 *                  <value>classpath:com/b5m/storage/dao/mapper/*.xml</value>
 *              </list>
 *          </property>
 *          <property name="plugins">
 *              <array>
 *                  <bean class="com.b5m.core.dao.interceptor.MapperInterceptor">
 *                      <property name="properties">
 *                          <value>
 *                              mappers=com.b5m.core.dao.mapper.CommonMapper
 *                          </value>
 *                      </property>
 *                  </bean>
 *              </array>
 *          </property>
 *       </bean>
 *
 *       注意: 使用该配置时, 会在方法执行时动态加载Bean对象, 在第一次调用方法时会有一定的延迟.
 *       建议采用{@link com.b5m.core.dao.spring.MapperScannerConfigurer}的配置方式, 在项目启动时完成初始化工作.
 * </p>
 *
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: chonglou
 * @version: 1.0
 * @createdate: 2015/12/28
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015/12/28  chonglou          1.0             Why & What is modified
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MapperInterceptor implements Interceptor {

    private final MapperHelper mapperHelper = new MapperHelper();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];
        String msId = ms.getId();
        //不需要拦截的方法直接返回
        if (mapperHelper.isMapperMethod(msId)) {
            //第一次经过处理后，就不会是ProviderSqlSource了，一开始高并发时可能会执行多次，但不影响。以后就不会在执行了
            if (ms.getSqlSource() instanceof ProviderSqlSource) {
                mapperHelper.setSqlSource(ms);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        int mapperCount = 0;
        String mapper = properties.getProperty("mappers");
        if (mapper != null && mapper.length() > 0) {
            String[] mappers = mapper.split(",");
            for (String mapperClass : mappers) {
                if (mapperClass.length() > 0) {
                    mapperHelper.registerMapper(mapperClass);
                    mapperCount++;
                }
            }
        }
        if (mapperCount == 0) {
            throw new RuntimeException("通用Mapper没有配置任何有效的通用接口!");
        }
    }
}
