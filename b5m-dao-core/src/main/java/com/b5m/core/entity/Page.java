/*
 * Copyright 2011-2015 B5M.COM. All rights reserved
 */
package com.b5m.core.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 带数据的分页接口
 * @author lucky.liu
 * @version v2.0.0 2014年9月26日 下午3:25:10
 * @param <T>
 */
public interface Page<T> extends Pageable, Cloneable, Serializable{
    
    public List<T> getContent();
    
}