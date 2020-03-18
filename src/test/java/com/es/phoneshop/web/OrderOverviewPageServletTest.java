package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;
import com.es.phoneshop.model.order.Payment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {

    @Mock
    private OrderDao orderDao;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private OrderOverviewPageServlet servlet;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();
        Order order = new Order();
        order.setSubtotalPrice(new BigDecimal(100));
        order.setDeliveryPrice(new BigDecimal(100));
        order.setPayment(Payment.CART);
        when(request.getPathInfo()).thenReturn(uuid.toString());
        when(orderDao.getOrder(any())).thenReturn(Optional.of(order));

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(stringArgumentCaptor.capture());
        assertEquals("/WEB-INF/pages/overview.jsp", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchOrderNotFoundException() throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();
        when(request.getPathInfo()).thenReturn(uuid.toString());
        when(orderDao.getOrder(any())).thenThrow(new OrderNotFoundException());

        servlet.doGet(request, response);

        verify(response).sendError(integerArgumentCaptor.capture());
        assertEquals(HttpServletResponse.SC_NOT_FOUND, (int) integerArgumentCaptor.getValue());
    }
}
