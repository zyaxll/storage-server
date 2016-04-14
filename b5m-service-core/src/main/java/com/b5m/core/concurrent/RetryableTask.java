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
public abstract class RetryableTask extends RunnableTask implements Retryable {

    /**
     * 最大重试次数
     */
    private int total;

    /**
     * 重试间隔
     */
    private long interval;

    /**
     * 当前次数
     */
    private int current;


    public RetryableTask(String name) {
        super(name);
        this.current = 0;
    }

    public void increaseCurrent() {
        this.current += 1;
    }

    public boolean canRetry(){
        return this.current < this.total;
    }

    @Override
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
