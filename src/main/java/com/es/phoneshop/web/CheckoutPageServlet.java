package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.EmptyCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.ProductUtil;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultCartService;
import com.es.phoneshop.service.DefaultOrderService;
import com.es.phoneshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;
    private BigDecimal deliveryPrice;

    @Override
    public void init() throws ServletException {
        orderService = DefaultOrderService.getInstance();
        cartService = DefaultCartService.getInstance();
        deliveryPrice = new BigDecimal(getServletConfig().getInitParameter("deliveryPrice"));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute("cart", cart);
        request.setAttribute("subtotalPrice", cartService.getTotalPrice(request, cart));
        request.setAttribute("deliveryPrice", deliveryPrice);
        request.setAttribute("totalPrice", deliveryPrice.add(cart.getTotal()));
        request.setAttribute("currency", ProductUtil.CURRENCY);
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Order order = orderService.placeOrder(request, deliveryPrice);
            response.getWriter().write(request.getRequestURI() + "/" + order.getId());
        } catch (IllegalArgumentException | EmptyCartException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }
}
