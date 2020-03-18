package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {

    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private List<CartItem> cart;
    private UUID id;
    private BigDecimal subtotalPrice;
    private BigDecimal deliveryPrice;

    public Order(String name, String surname, String address, String phoneNumber, List<CartItem> cart, UUID id,
                 BigDecimal subtotalPrice, BigDecimal deliveryPrice) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cart = cart;
        this.id = id;
        this.subtotalPrice = subtotalPrice;
        this.deliveryPrice = deliveryPrice;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
