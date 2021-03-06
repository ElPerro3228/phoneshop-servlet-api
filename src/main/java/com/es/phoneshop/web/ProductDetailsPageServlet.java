package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.NegativeNumberException;
import com.es.phoneshop.exceptions.ProductAddToCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exceptions.ProductNotFoundException;

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
            quantity = getQuantity(request);
            addProductToCart(request, cart, quantity);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(cartService.getCart(request).toString());
        } catch (ProductAddToCartException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Not a number");
        }
    }

    private void addProductToCart(HttpServletRequest request, Cart cart, int quantity) {
        try {
            Long id = Long.valueOf(request.getPathInfo().substring(1));
            cartService.add(cart, id, quantity);
        } catch (OutOfStockException e) {
            throw new ProductAddToCartException("Not enough stock, available " + e.getProduct().getStock());
        } catch (NegativeNumberException e) {
            throw new ProductAddToCartException(e.getMessage());
        }
    }

    private int getQuantity(HttpServletRequest request) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
        return numberFormat.parse(request.getParameter("quantity")).intValue();
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