package com.q1sj.log.core;

import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Q1sj
 * @date 2022.4.29 9:20
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogTest {

    static ExecutorService threadPool = new TraceExecutorServiceDecorator(Executors.newFixedThreadPool(1));

    @Test
    public void testAppender() {
        LoggerContext lc = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        MyLogAppender myLogAppender = new MyLogAppender();
        myLogAppender.setName("myLogAppender");

        myLogAppender.setContext(lc);
        myLogAppender.start();
        ch.qos.logback.classic.Logger logger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(myLogAppender);
        log.debug("debug");
    }

    @Test
    public void testTrace() throws IOException {

        TraceUtils.putId();

        log.info("test");
        log.debug("debug");
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        new TraceThread(() -> {
//            MDC.setContextMap(copyOfContextMap);
            log.debug("new thread start");
//            MDC.clear();
        }).start();

        threadPool.submit(() -> log.debug("thread pool submit"));
        threadPool.execute(() -> log.debug("thread pool execute"));
        System.in.read();
    }
}

class TraceThread extends Thread {
    public TraceThread(Runnable target) {
        super(TraceUtils.packing(target));
    }
}
