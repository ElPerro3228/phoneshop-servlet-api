package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);
    void add(Cart cart, Long productId, int quantity) throws OutOfStockException;
    void calculateTotalPrice(Cart cart);
    void update(Cart cart, Long productId, int quantity);
    void delete(Cart cart, long productId);
    String getTotalPrice(HttpServletRequest request, Cart cart);
}
