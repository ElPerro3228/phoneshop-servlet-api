package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exceptions.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryServlet extends AbstractProductServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = processId(request);
            addProductToRecentWatched(product, request);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(request, response);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
