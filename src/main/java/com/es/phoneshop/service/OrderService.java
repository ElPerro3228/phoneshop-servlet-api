package com.es.phoneshop.service;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.Payment;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public interface OrderService {
    Order placeOrder(HttpServletRequest request, BigDecimal deliveryPrice);
    Payment getPayment(HttpServletRequest request);
}
