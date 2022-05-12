package com.q1sj.log.core;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author Q1sj
 * @date 2022.5.12 11:27
 */
public class TracePreZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        String id = TraceUtils.nextId();
        TraceUtils.putId(id);
        context.addZuulRequestHeader(TraceUtils.MDC_TRACE_ID_KEY, id);
        return null;
    }
}
