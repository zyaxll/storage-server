package com.b5m.core.entity;

/**
 * @description: 查询方式
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-28
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-28       Leo.li          1.0             TODO
 */
public enum Mode {

    /**
     * 为空
     */
    NULL,

    /**
     * 不为空
     */
    NOTNULL,

    /**
     * 相等
     */
    EQ,

    /**
     * 不相等
     */
    NE,

    /**
     * 模糊匹配
     */
    LIKE,

    /**
     * 字符串头部精确匹配，尾部模糊匹配
     */
    LIKESTART,

    /**
     * 字符串头部模糊匹配，尾部精确匹配
     */
    LIKEEND,

    /**
     * 忽略大小写模糊匹配
     */
    ILIKE,

    /**
     * 忽略大小写尾部模糊匹配
     */
    ILIKESTART,

    /**
     * 忽略大小写头部模糊匹配
     */
    ILIKEEND,

    /**
     * 大于
     */
    GT,

    /**
     * 大于等于
     */
    GTE,

    /**
     * 小于
     */
    LT,

    /**
     * 小于等于
     */
    LTE,

    /**
     * 集合或数组中存在
     */
    IN;

}
