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
class Task1 extends CallableTask<Integer> {

    public Task1(String name) {
        super(name);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(getName());
        Thread.sleep(10000);
        System.out.println(getName() + " sleep 10000.");
        return 1;
    }

}
