package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItems;
    private BigDecimal total;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Cart[" + cartItems + "]";
    }
}
