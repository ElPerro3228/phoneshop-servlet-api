package com.es.phoneshop.model.order;

public enum Payment {
    CART("Cart"), CASH("Cash");

    private String value;

    Payment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
