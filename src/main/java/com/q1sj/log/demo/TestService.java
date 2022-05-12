package com.q1sj.log.demo;

import com.q1sj.log.core.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Q1sj
 * @date 2022.5.5 10:01
 */
@Slf4j
@Service
public class TestService {
    @Trace
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

}
