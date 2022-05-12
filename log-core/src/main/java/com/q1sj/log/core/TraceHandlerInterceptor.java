package com.q1sj.log.core;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Q1sj
 * @date 2022.5.5 9:53
 */
public class TraceHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(TraceUtils.MDC_TRACE_ID_KEY);
        if (StringUtils.isEmpty(traceId)) {
            TraceUtils.putId();
        } else {
            TraceUtils.putId(traceId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TraceUtils.removeId();
    }
}
