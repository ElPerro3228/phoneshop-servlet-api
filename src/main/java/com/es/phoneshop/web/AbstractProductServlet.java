package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public abstract class AbstractProductServlet extends HttpServlet {

    protected ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    protected Product processId(HttpServletRequest request) throws IOException {
        Long id = Long.valueOf(request.getPathInfo().substring(1));
        return  productDao.getProduct(id).orElseThrow(ProductNotFoundException::new);
    }

}
