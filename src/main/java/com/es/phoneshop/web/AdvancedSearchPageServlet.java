package com.es.phoneshop.web;

import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;
import com.es.phoneshop.service.DefaultProductService;
import com.es.phoneshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AdvancedSearchPageServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = DefaultProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> products = getProducts(request);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "not a number");
            request.setAttribute("products", Collections.emptyList());
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        }
    }

    private List<Product> getProducts(HttpServletRequest request) {
        String query = request.getParameter("query");
        String maxPrice = request.getParameter("maxPrice");
        String minPrice = request.getParameter("minPrice");
        String searchType = request.getParameter("searchType");
        return productService.createProductList(query, maxPrice, minPrice, searchType);
    }
}
