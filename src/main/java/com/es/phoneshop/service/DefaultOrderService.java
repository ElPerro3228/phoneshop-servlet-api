package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.EmptyCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;
import com.es.phoneshop.model.order.Payment;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DefaultOrderService implements OrderService {

    private static DefaultOrderService instance;
    private OrderDao orderDao;
    private CartService cartService;
    private Map<String, Payment> paymentMap;


    private DefaultOrderService(){
        orderDao = ArrayListOrderDao.getInstance();
        cartService = DefaultCartService.getInstance();
        paymentMap = new HashMap<>();
        paymentMap.put("Cart", Payment.CART);
        paymentMap.put("Cash", Payment.CASH);
    }

    public static DefaultOrderService getInstance(){
        if (instance == null) {
            instance = new DefaultOrderService();
        }
        return instance;
    }

    @Override
    public Order placeOrder(HttpServletRequest request, BigDecimal deliveryPrice) {
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
        Payment payment = getPayment(request);
        Order order = new Order(name, surname, address, phone, products, id, subtotalPrice, deliveryPrice, payment);
        orderDao.save(order);
        cart.getCartItems().clear();
        return order;
    }

    @Override
    public Payment getPayment(HttpServletRequest request) {
        String value = request.getParameter("payment");
        if (value.equals("")) {
            throw new IllegalArgumentException("Please fill out all fields");
        }
        return paymentMap.get(value);
    }

    private String getValue(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value.equals("")) {
            throw new IllegalArgumentException("Please fill out all fields");
        }
        return value;
    }
}
