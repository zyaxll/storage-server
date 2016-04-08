package com.b5m.core.concurrent;

import java.util.List;

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
public class Main {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        List<Integer> list = TaskExecutor.call(new Task1("task1"), new Task2("task2"));
        System.out.println("total:" + (System.currentTimeMillis() - begin) / 1000.0);
        System.out.println(list);
    }

}
