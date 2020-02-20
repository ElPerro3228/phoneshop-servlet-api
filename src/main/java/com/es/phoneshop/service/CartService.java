package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);
    void add(Cart cart, Long productId, int quantity) throws OutOfStockException;

}
