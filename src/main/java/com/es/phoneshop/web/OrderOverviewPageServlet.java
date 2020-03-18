package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;
import com.es.phoneshop.model.product.ProductUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class OrderOverviewPageServlet extends HttpServlet {

    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String stringId = request.getPathInfo().substring(1);
            Order order = orderDao.getOrder(UUID.fromString(stringId)).orElseThrow(OrderNotFoundException::new);
            request.setAttribute("cartPrice", order.getSubtotalPrice());
            request.setAttribute("deliveryPrice", order.getDeliveryPrice());
            request.setAttribute("totalPrice", order.getSubtotalPrice().add(order.getDeliveryPrice()));
            request.setAttribute("name", order.getName());
            request.setAttribute("surname", order.getSurname());
            request.setAttribute("address", order.getAddress());
            request.setAttribute("phone", order.getPhoneNumber());
            request.setAttribute("cart", order.getCart());
            request.setAttribute("currency", ProductUtil.CURRENCY);
            request.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(request, response);
        } catch (OrderNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
