package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryServlet extends HttpServlet {

    ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getPathInfo().substring(1));
        Product product = productDao.getProduct(id).get();
        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(req, resp);
    }
}
