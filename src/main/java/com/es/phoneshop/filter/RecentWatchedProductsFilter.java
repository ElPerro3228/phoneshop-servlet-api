package com.es.phoneshop.filter;

import com.es.phoneshop.service.RecentWatchedProductService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RecentWatchedProductsFilter implements Filter {

    private RecentWatchedProductService recentWatchedProductService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        recentWatchedProductService = RecentWatchedProductService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setAttribute("resentWatched", recentWatchedProductService.getRecentWatchProducts(request).getProducts());
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
