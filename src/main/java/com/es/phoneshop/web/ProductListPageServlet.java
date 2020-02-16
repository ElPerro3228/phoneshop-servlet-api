package com.es.phoneshop.web;


import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;
import com.es.phoneshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        orderService = OrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Product> products = getProducts(query, request);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> getProducts(String query, HttpServletRequest req) {
        String field = req.getParameter("field");
        String order = req.getParameter("order");
        SortOrder sortOrder = orderService.getSortOrder(order);
        SortField sortField = orderService.getSortField(field);
        return orderService.createProductList(query, sortOrder, sortField);
    }

}


