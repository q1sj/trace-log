package com.q1sj.log.demo;

import com.q1sj.log.core.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Q1sj
 * @date 2022.5.5 9:59
 */
@Slf4j
@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @Trace
    @GetMapping("/debug")
    public String debug() {
        log.debug("debug");
        testService.asyncDebug();
        testService.debug();
        log.debug("debug2");
        return "ok";
    }

    @GetMapping("/noTrace")
    public String noTrace() {
        log.debug("debug");
        testService.debug();
        testService.debug();
        log.debug("debug2");
        return "ok";
    }

}
