package com.q1sj.log.demo;

import com.q1sj.log.core.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Q1sj
 * @date 2022.5.5 10:01
 */
@Slf4j
@Service
public class TestService {

    public void debug() {
        log.debug("service debug");
    }

    @Async
    public void asyncDebug() {
        /*try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        log.debug("@Async service debug");
    }

    public void recursion(int i) {
        if (i > 2) {
            return;
        }
        log.debug("recursion {}", i);
        recursion(++i);
    }
}
