package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.recentwatched.RecentWatchedProducts;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultCartService;
import com.es.phoneshop.service.RecentWatchedProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public abstract class AbstractProductServlet extends HttpServlet {

    protected ProductDao productDao;
    protected CartService cartService;
    protected RecentWatchedProductService recentWatchedProductService;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentWatchedProductService = RecentWatchedProductService.getInstance();
    }

    protected Product processId(HttpServletRequest request) throws IOException {
        Long id = Long.valueOf(request.getPathInfo().substring(1));
        return productDao.getProduct(id).orElseThrow(ProductNotFoundException::new);
    }

    protected void addProductToRecentWatched(Product product, HttpServletRequest request) {
        RecentWatchedProducts recentWatchedProducts = recentWatchedProductService.getRecentWatchProducts(request);
        recentWatchedProductService.addProduct(recentWatchedProducts, product);
    }
}
