package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;
import com.es.phoneshop.service.DefaultProductService;
import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.RecentWatchedProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ProductService defaultProductService;

    @Override
    public void init() {
        defaultProductService = DefaultProductService.getInstance();
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
        SortOrder sortOrder = getSortOrder(order);
        SortField sortField = getSortField(field);
        return defaultProductService.createProductList(query, sortOrder, sortField);
    }

    public SortOrder getSortOrder(String order) {
        return order == null ? null : order.equals("asc") ? SortOrder.asc : SortOrder.desc;
    }

    public SortField getSortField(String field) {
        return field == null ? null : field.equals("price") ? SortField.price : SortField.description;
    }
}


