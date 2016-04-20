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
public abstract class RunnableTask implements Runnable {

    protected String name;
    protected long delay;

    public RunnableTask(String name) {
        this.name = name;
    }

    public RunnableTask(String name, long delay) {
        this(name);
        this.delay = delay >= 0 ? delay : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
