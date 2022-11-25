package com.q1sj.log.core;

import com.q1sj.log.core.TraceUtils;
import com.q1sj.log.demo.Demo1Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author Q1sj
 * @date 2022.11.25 13:34
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Demo1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TtlTest {
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Test
    public void test() {
        for (int i = 0; i < 3; i++) {
            TraceUtils.putId();
            traceId();
            TraceUtils.removeId();
        }
    }

    @Test
    public void traceId() {
        log.info(TraceUtils.currentId());
        new Thread(() -> log.info(TraceUtils.currentId())).start();
        Runnable task = () -> {
            log.info(TraceUtils.currentId());
            TraceUtils.removeId();
        };
        executorService.submit(task);
        executorService.submit(task);
        IntStream.range(1, 5).parallel().forEach(i -> log.info("" + i));
    }
}
