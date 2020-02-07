package com.es.phoneshop.web;

import com.es.phoneshop.model.product.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Product> products = getProducts(query, request);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> getProducts(String query, HttpServletRequest req){
        String field = req.getParameter("field");
        String order = req.getParameter("order");
        SortOrder sortOrder = order == null ? null : order.equals("asc") ? SortOrder.asc : SortOrder.desc;
        SortField sortField = field == null ? null : field.equals("price") ? SortField.price : SortField.description;
        if (query == null || "".equals(query)) {
            return productDao.findProducts(p -> ((p.getPrice().doubleValue() > 0) && (p.getStock() > 0)), sortField, sortOrder);
        }
        return productDao.findProducts(p -> ProductDaoUtil.countMatches(p, query), sortField, sortOrder);
    }

}


