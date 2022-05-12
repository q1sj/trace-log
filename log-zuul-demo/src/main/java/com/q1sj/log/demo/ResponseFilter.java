package com.q1sj.log.demo;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.q1sj.log.core.TraceUtils;
import org.apache.catalina.filters.RequestFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义网关过滤器
 *
 * @author Q1sj
 */
@Component
public class ResponseFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    /**
     * 过滤方法
     */
    @Override
    public Object run() {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();

        String uri = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        String requestBody = getRequestBody();
        String responseBody = getResponseBody();
        logger.info("请求路径{},请求方式{} params:{}\nbody:{}\nresp:{}",
                uri, method,
                queryString, requestBody, responseBody);
        return null;
    }

    public String getRequestBody() {
        // 获取Request上下文
        RequestContext rc = RequestContext.getCurrentContext();

        HttpServletRequest request = rc.getRequest();
        try {
            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
            reader.close();
            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否开启过滤:默认false
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 多个过滤器中的执行顺序，数值越小，优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器的类型
     */
    @Override
    public String filterType() {
        return "post";
    }


    public String getResponseBody() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object zuulResponse = ctx.get("zuulResponse");
        if (zuulResponse instanceof RibbonHttpResponse){
            MediaType mediaType = ((RibbonHttpResponse) zuulResponse).getHeaders().getContentType();
            // 只读取json resp
            if (mediaType == null
                    || !mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                return "ContentType not application/json";
            }
        }
        String ret = ctx.getResponseBody();
        if (StringUtils.isNotBlank(ret)) {
            return ret;
        }
        InputStream is = ctx.getResponseDataStream();
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            ret = result.toString();
            ctx.setResponseBody(ret);
            return ret;
        } catch (IOException ex) {
            // ignore
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
            }
        }
        return null;
    }
}
