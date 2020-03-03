package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recentwatched.RecentWatchedProducts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

public class RecentWatchedProductService {

    private static final int MAX_RECENT_WATCHED_LIST_SIZE = 3;
    private static final String RECENT_WATCHED_PRODUCTS_ATTRIBUTE = "recentWatchedProducts";

    private static RecentWatchedProductService instance;


    private RecentWatchedProductService() {

    }

    public static RecentWatchedProductService getInstance() {
        if (instance == null) {
            instance = new RecentWatchedProductService();
        }
        return instance;
    }

    public void addProduct(RecentWatchedProducts recentWatchedProducts, Product product) {
        synchronized (recentWatchedProducts) {
            LinkedList<Product> products = (LinkedList<Product>) recentWatchedProducts.getProducts();
            if (!isDuplicate(products, product)) {
                products.addFirst(product);
            }
            if (products.size() > MAX_RECENT_WATCHED_LIST_SIZE) {
                products.removeLast();
            }
        }
    }

    public RecentWatchedProducts getRecentWatchProducts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        synchronized (session) {
            RecentWatchedProducts recentWatchedProducts = (RecentWatchedProducts) session.getAttribute(RECENT_WATCHED_PRODUCTS_ATTRIBUTE);
            if (recentWatchedProducts == null) {
                recentWatchedProducts = new RecentWatchedProducts();
                session.setAttribute(RECENT_WATCHED_PRODUCTS_ATTRIBUTE, recentWatchedProducts);
            }
            return recentWatchedProducts;
        }
    }

    private boolean isDuplicate(List<Product> products, Product product) {
        return products.stream()
                .anyMatch(p -> product.getId().equals(p.getId()));
    }
}
