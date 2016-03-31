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
