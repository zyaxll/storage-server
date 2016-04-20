package com.b5m.core.concurrent;

import com.b5m.utils.Assert;

import java.util.concurrent.Callable;

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
public abstract class CallableTask<V> implements Callable<V> {

    protected String name;

    public CallableTask(String name) {
        Assert.hasText(name, "task name not null");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
