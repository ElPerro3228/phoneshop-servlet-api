package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public abstract class IdProcessingPageServlet extends HttpServlet {

    protected ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    protected Product processId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.valueOf(request.getPathInfo().substring(1));
        Optional<Product> optionalProduct = productDao.getProduct(id);
        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException();
        }
        return  optionalProduct.get();
    }
}