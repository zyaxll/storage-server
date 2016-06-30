package com.b5m.core.concurrent;

import com.b5m.utils.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
@Component
public final class TaskExecutor {

    private final static Logger LOGGER = LogManager.getLogger(TaskExecutor.class);

    /**
     * 从config.properties中读取task.pool.size
     */
    private static int POOL_SIZE;

    @Value("#{config['task.pool.size']}")
    private int localPoolSize;

    private static final int DEFAULT_POOL_SIZE = 50;

    /**
     * 默认10秒超时
     */
    private static final int DEFAULT_TIMEOUT = 10000;

    private static final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(0 == POOL_SIZE ? DEFAULT_POOL_SIZE : POOL_SIZE);

    public static void run(List<RunnableTask> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }

        for (RunnableTask task : tasks) {
            run(task);
        }
    }

    public static void run(RunnableTask... tasks) {
        if (null == tasks || tasks.length == 0) {
            return;
        }

        for (RunnableTask task : tasks) {
            run(task);
        }
    }

    public static void run(RunnableTask task) {
        if (task != null) {
            LOGGER.info("准备执行任务:[{}]", task.getName());

            try {
                if(task.getDelay() > 0){
                    LOGGER.info("任务[{}]将在延迟[{}]毫秒后被执行", task.getName(), task.getDelay());
                    threadPool.schedule(task, task.getDelay(), TimeUnit.MILLISECONDS);
                } else {
                    threadPool.execute(task);
                }
            } catch (Exception e) {
                LOGGER.error("任务:[" + task.getName() + "]执行失败", e);
            }
        }
    }

    public static void runFixed(List<RunnableTask> tasks, long delay) {
        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }

        for (RunnableTask task : tasks) {
            runFixed(task, delay);
        }
    }

    public static void runFixed(RunnableTask task, long delay) {
        if (task != null) {
            LOGGER.info("准备执行任务:[{}]", task.getName());

            try {
                LOGGER.info("任务[{}]将在延迟[{}]毫秒后被执行", task.getName(), task.getDelay());
                threadPool.scheduleAtFixedRate(task, task.getDelay(), delay, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                LOGGER.error("任务:[" + task.getName() + "]执行失败", e);
            }
        }
    }

    public static <T> List<T> call(List<Callable<T>> tasks) {
       return call(tasks, DEFAULT_TIMEOUT);
    }

    public static <T> List<T> call(List<Callable<T>> tasks, int timeout) {
        return call(tasks, timeout, true);
    }


    public static <T> List<T> call(List<Callable<T>> tasks, boolean isSkipEmpty) {
        return call(tasks, DEFAULT_TIMEOUT, isSkipEmpty);
    }

    public static <T> List<T> call(List<Callable<T>> tasks, int timeout, boolean isSkipEmpty) {
        if (CollectionUtils.isEmpty(tasks)) {
            return null;
        }

        List<T> result = new ArrayList<>(tasks.size());
        try {
            List<Future<T>> futures = threadPool.invokeAll(tasks, timeout, TimeUnit.MILLISECONDS);
            for (Future<T> future : futures) {
                T obj = future.get();
                if (isSkipEmpty && null != obj) {
                    result.add(obj);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("CallableTask error", e);
            return null;
        }

        return result;
    }

    public static <T> List<T> call(Callable<T>... tasks) {
        return call(Arrays.asList(tasks));
    }

    public static <T> T call(CallableTask<T> task) {
        if (task != null) {
            LOGGER.info("准备执行任务:[{}]", task.getName());

            try {
                Future<T> result = threadPool.submit(task);
                return result.get();
            } catch (Exception e) {
                LOGGER.error("任务:[" + task.getName() + "]执行失败", e);
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @PostConstruct
    private void init() {
        POOL_SIZE = this.localPoolSize;
    }

    @PreDestroy
    private void destroy() {
        threadPool.shutdown();
    }

}
