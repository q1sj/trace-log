package com.q1sj.log.core;

import com.q1sj.log.core.TraceUtils;
import com.q1sj.log.demo.Demo1Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    public void test() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            TraceUtils.putId();
            traceId();
            TraceUtils.removeId();
        }
    }

    @Test
    public void traceId() throws InterruptedException {
        TraceUtils.putId();
        String mainId = TraceUtils.currentId();
        log.info(mainId);
        String[] newThreadTraceId = new String[1];
        new Thread(() -> {
            newThreadTraceId[0] = TraceUtils.currentId();
            log.info(newThreadTraceId[0]);
        }).start();
        int count = 2;
        String[] threadPoolIds = new String[count];
        for (int i = 0; i < count; i++) {
            int finalI = i;
            executorService.submit(() -> {
                threadPoolIds[finalI] = TraceUtils.currentId();
                log.info("mainId {} threadId {}", mainId, threadPoolIds[finalI]);
                TraceUtils.removeId();
            });
        }
        int streamCount = 5;
        String[] streamIds = new String[streamCount];
        IntStream.range(0, streamCount).parallel().forEach(i -> {
            streamIds[i] = TraceUtils.currentId();
            log.info("mainId {} streamId {}", mainId, streamIds[i]);
            Assert.assertEquals(mainId, streamIds[i]);
        });
        TimeUnit.SECONDS.sleep(2);

        Assert.assertEquals(mainId, newThreadTraceId[0]);

        for (String threadPoolId : threadPoolIds) {
            Assert.assertEquals(mainId, threadPoolId);
        }

        for (String streamId : streamIds) {
            Assert.assertEquals(mainId, streamId);
        }
    }
}
