package com.q1sj.log.core;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import sun.tools.jar.resources.jar;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author Q1sj
 * @date 2022.11.25 10:23
 */
@Slf4j
public class TtlTest {


    //-javaagent:D:\Code\log\log-core\lib\transmittable-thread-local-2.14.2.jar
    public static void main(String[] args) {
//        traceId();
        // ## 1. 框架上层逻辑，后续流程框架调用业务 ##
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
//        ThreadLocal<String> context = new ThreadLocal<>();
//        InheritableThreadLocal<String> context = new InheritableThreadLocal<>();
        context.set("value-set-in-parent");

        // ## 2. 应用逻辑，后续流程业务调用框架下层逻辑 ##
        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));

        Runnable task = () -> {
            log.info(context.get());
            context.remove();
        };
        executorService.submit(task);
        executorService.submit(task);

        // ## 3. 框架下层逻辑 ##
        // Task或是Call中可以读取，值是"value-set-in-parent"
        executorService.shutdown();

        String value = context.get();
        log.info(value);
    }

    @Test
    public void traceId() {
        TraceUtils.putId();
        log.info(TraceUtils.currentId());
        new Thread(() -> {
            log.info(TraceUtils.currentId());
        }).start();
    }
}
