package com.q1sj.log.demo;

import com.q1sj.log.core.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Q1sj
 * @date 2022.5.9 16:48
 */
@Slf4j
@EnableScheduling
//@Component
public class TestTask {
    @Autowired
    private TestService testService;

    @Trace
    @Scheduled(fixedRate = 30000)
    public void run(){
        log.debug("debug");
        testService.asyncDebug();
        testService.debug();
        testService.debug();
        testService.recursion(0);
        log.debug("debug2");
        log.error("test exception", new RuntimeException("test"));
    }
}
