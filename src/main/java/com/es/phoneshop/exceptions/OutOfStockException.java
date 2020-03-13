package com.es.phoneshop.exceptions;

import com.es.phoneshop.model.product.Product;

public class OutOfStockException extends RuntimeException{

    private Product product;
    private int quantityRequested;

    public OutOfStockException(Product product, int quantityRequested) {
        this.product = product;
        this.quantityRequested = quantityRequested;
    }

    public OutOfStockException(String message, Product product, int quantityRequested) {
        super(message);
        this.product = product;
        this.quantityRequested = quantityRequested;
    }

    public Product getProduct() {
        return product;
    }
}
