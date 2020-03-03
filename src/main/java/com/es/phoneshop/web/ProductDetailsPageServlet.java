package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends AbstractProductServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ProductNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        int quantity = 0;
        try {
            quantity = getQuantity(request, quantity);
            addProductToCart(request, cart, quantity);
            response.sendRedirect(request.getRequestURI() + "?success=true");
        } catch (ProductAddToCartException e) {
            request.setAttribute("error", e.getMessage());
            processRequest(request, response);
        }
    }

    private void addProductToCart(HttpServletRequest request, Cart cart, int quantity) {
        try {
            Long id = Long.valueOf(request.getPathInfo().substring(1));
            cartService.add(cart, id, quantity);
        } catch (OutOfStockException e) {
            throw new ProductAddToCartException("Not enough stock, available " + e.getProduct().getStock());
        }
    }

    private int getQuantity(HttpServletRequest request, int quantity) {
        try {
            NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
            quantity = numberFormat.parse(request.getParameter("quantity")).intValue();
        } catch (ParseException e) {
            throw new ProductAddToCartException("Not a number");
        }
        return quantity;
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Product product = processId(request);
        addProductToRecentWatched(product, request);
        request.setAttribute("product", product);
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("resentWatched", recentWatchedProductService.getRecentWatchProducts(request).getProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }
}