package com.b5m.core.concurrent;

/**
 * @description: TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-4-8
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-4-8       Leo.li          1.0             TODO
 */
public interface Retryable {

    /**
     * 获取可重试次数
     * @return 可重试次数
     */
    int getTotal();

    /**
     * 获取重试时间间隔(毫秒)
     * @return 重试时间间隔(毫秒)
     */
    long getInterval();

}
