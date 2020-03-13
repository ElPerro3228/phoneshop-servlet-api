package com.es.phoneshop.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DosFilter implements Filter {

    private Map<String, RequestsCounter> requestsMap;
    private int timeOut;
    private int maxRequestsQuantity;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        timeOut = Integer.parseInt(filterConfig.getInitParameter("timeOut"));
        maxRequestsQuantity = Integer.parseInt(filterConfig.getInitParameter("maxRequestsQuantity"));
        requestsMap = new HashMap<>();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String ip = request.getRemoteAddr();
        if (isValid(ip)) {
            chain.doFilter(request, servletResponse);
        } else {
            request.getRequestDispatcher("WEB-INF/pages/dosPage.jsp").forward(request, servletResponse);
        }
    }

    private boolean isValid(String ip) {
        boolean result = true;
        if (requestsMap.get(ip) == null) {
            requestsMap.put(ip, new RequestsCounter(System.currentTimeMillis(), 0));
        }
        RequestsCounter counter = requestsMap.get(ip);
        if (System.currentTimeMillis() - counter.lastTime > timeOut) {
            counter.lastTime = System.currentTimeMillis();
            counter.quantity = 0;
        }
        counter.quantity++;
        if (counter.quantity > maxRequestsQuantity) {
            result = false;
        }
        return result;
    }

    @Override
    public void destroy() {

    }

    private class RequestsCounter {
        public long lastTime;
        public int quantity;

        public RequestsCounter() {
        }

        public RequestsCounter(long lastTime, int quantity) {
            this.lastTime = lastTime;
            this.quantity = quantity;
        }
    }
}
