package com.es.phoneshop.exceptions;

public class ProductAddToCartException extends RuntimeException{

    public ProductAddToCartException(String message) {
        super(message);
    }
}
