package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.EmptyCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;
import com.es.phoneshop.model.product.ProductUtil;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckoutPageServlet extends HttpServlet {

    private OrderDao orderDao;
    private CartService cartService;
    private BigDecimal deliveryPrice;

    @Override
    public void init() throws ServletException {
        orderDao = ArrayListOrderDao.getInstance();
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
            Order order = getOrder(request);
            response.getWriter().write(request.getRequestURI() + "/" + order.getId());
        } catch (IllegalArgumentException | EmptyCartException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }

    private Order getOrder(HttpServletRequest request) {
        Cart cart = cartService.getCart(request);
        List<CartItem> products = new ArrayList<>(cart.getCartItems());
        if (products.size() == 0) {
            throw new EmptyCartException("Your cart is empty");
        }
        String name = getValue(request, "name");
        String surname = getValue(request, "surname");
        String address = getValue(request, "address");
        String phone = getValue(request, "phone");
        UUID id = UUID.randomUUID();
        BigDecimal subtotalPrice = cart.getTotal();
        Order order = new Order(name, surname, address, phone, products, id, subtotalPrice, deliveryPrice);
        orderDao.save(order);
        cart.getCartItems().clear();
        return order;
    }

    private String getValue(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value.equals("")) {
            throw new IllegalArgumentException("Please fill out all fields");
        }
        return value;
    }
}
