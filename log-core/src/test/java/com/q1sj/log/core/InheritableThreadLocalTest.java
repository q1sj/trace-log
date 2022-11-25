package com.q1sj.log.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Q1sj
 * @date 2022.11.25 13:56
 */
@Slf4j
public class InheritableThreadLocalTest {
    public static void main(String[] args) {
        InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("main set");
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(()->{
            log.info(threadLocal.get());
            threadLocal.remove();
        });
        pool.submit(()->{
            log.info(threadLocal.get());
            threadLocal.remove();
        });

    }
}
