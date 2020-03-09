package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.NegativeNumberException;
import com.es.phoneshop.exceptions.ProductAddToCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.CartItemNotfoundException;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultCartService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CartPageServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        cartService.calculateTotalPrice(cart);
        request.setAttribute("cart", cart);
        request.setAttribute("price", cartService.getTotalPrice(request, cart));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        Cart cart = cartService.getCart(request);
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            JSONObject jsonObject = getJsonObject(reader);
            tryUpdateCart(cart, jsonObject);
            cartService.calculateTotalPrice(cart);
            response.getWriter().write(cartService.getTotalPrice(request, cart));
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Not a number");
        } catch (ProductAddToCartException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }

    private void tryUpdateCart(Cart cart, JSONObject jsonObject) {
        try {
            int quantity = Integer.parseInt(jsonObject.get("quantity").toString());
            long id = Long.parseLong(jsonObject.get("id").toString());
            cartService.update(cart, id, quantity);
        } catch (NumberFormatException e) {
            throw new ProductAddToCartException("Something's wrong");
        } catch (OutOfStockException | NegativeNumberException e) {
            throw new ProductAddToCartException(e.getMessage());
        } catch (CartItemNotfoundException e) {
            throw new ProductAddToCartException("Cart item was deleted");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        Cart cart = cartService.getCart(request);
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            JSONObject jsonObject = getJsonObject(reader);
            long id = Long.parseLong(jsonObject.get("id").toString());
            cartService.delete(cart, id);
            cartService.calculateTotalPrice(cart);
            response.getWriter().write(cartService.getTotalPrice(request, cart));
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Something's wrong");
        }
    }

    private JSONObject getJsonObject(Reader reader) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }
}
