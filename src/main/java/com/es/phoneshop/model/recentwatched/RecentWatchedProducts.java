package com.es.phoneshop.model.recentwatched;

import com.es.phoneshop.model.product.Product;

import java.util.LinkedList;
import java.util.List;

public class RecentWatchedProducts {

    private List<Product> products;

    public RecentWatchedProducts() {
        products = new LinkedList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
