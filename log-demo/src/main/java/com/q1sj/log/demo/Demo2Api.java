package com.q1sj.log.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Q1sj
 * @date 2022.5.12 11:20
 */
@FeignClient(name = "demo2",url = "127.0.0.1:8012")
public interface Demo2Api {
    @GetMapping("/debug")
    String debug();
}
