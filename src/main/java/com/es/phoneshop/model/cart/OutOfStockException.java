package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

public class OutOfStockException extends RuntimeException{

    private Product product;
    private int quantityRequested;

    public OutOfStockException(Product product, int quantityRequested) {
        this.product = product;
        this.quantityRequested = quantityRequested;
    }

    public Product getProduct() {
        return product;
    }
}
