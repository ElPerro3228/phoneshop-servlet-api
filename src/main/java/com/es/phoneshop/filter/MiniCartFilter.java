package com.es.phoneshop.filter;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultCartService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MiniCartFilter implements Filter {

    private CartService cartService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        cartService = DefaultCartService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cart cart = cartService.getCart(request);
        request.setAttribute("miniCart", cart.getCartItems().size());
        chain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
