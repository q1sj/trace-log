package com.q1sj.log.core;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author Q1sj
 * @date 2022.5.12 11:27
 */
public class TracePostZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            context.addZuulResponseHeader(TraceUtils.MDC_TRACE_ID_KEY,TraceUtils.currentId());
        } finally {
            TraceUtils.removeId();
        }
        return null;
    }
}
