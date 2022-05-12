package com.q1sj.log.core;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 使用openfeign调用接口时 在请求头加入traceId
 * @author Q1sj
 * @date 2022.5.12 11:16
 */
public class TraceFeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(TraceUtils.MDC_TRACE_ID_KEY, TraceUtils.currentId());
    }
}
