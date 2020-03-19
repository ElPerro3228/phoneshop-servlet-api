package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.EmptyCartException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {

    private static final BigDecimal TOTAL_PRICE = new BigDecimal(100);
    private static final BigDecimal DELIVERY_PRICE = new BigDecimal(20);
    private static final String STRING_TOTAL_PRICE = "100 USD";

    @Spy
    private BigDecimal deliveryPrice = new BigDecimal(20);
    @Mock
    private PrintWriter writer;
    @Mock
    private CartService cartService;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private CheckoutPageServlet servlet;
    @Captor
    private ArgumentCaptor<BigDecimal> bigDecimalArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setup() throws ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem(new Product(), 1));
        cart.setTotal(TOTAL_PRICE);
        when(cartService.getCart(request)).thenReturn(cart);
        when(cartService.getTotalPrice(request, cart)).thenReturn(STRING_TOTAL_PRICE);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("subtotalPrice"), stringArgumentCaptor.capture());
        verify(request).setAttribute(eq("deliveryPrice"), bigDecimalArgumentCaptor.capture());
        verify(request).setAttribute(eq("totalPrice"), bigDecimalArgumentCaptor.capture());

        assertEquals(STRING_TOTAL_PRICE, stringArgumentCaptor.getValue());
        assertEquals(DELIVERY_PRICE, bigDecimalArgumentCaptor.getAllValues().get(0));
        assertEquals(DELIVERY_PRICE.add(TOTAL_PRICE), bigDecimalArgumentCaptor.getAllValues().get(1));
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        when(response.getWriter()).thenReturn(writer);
        when(request.getRequestURI()).thenReturn("test");
        when(orderService.placeOrder(request, deliveryPrice)).thenReturn(order);
        servlet.doPost(request, response);

        verify(writer).write(stringArgumentCaptor.capture());

        assertEquals("test/" + order.getId(), stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchEmptyCartException() throws ServletException, IOException {
        when(orderService.placeOrder(request, deliveryPrice)).thenThrow(new EmptyCartException("Your cart is empty"));
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(writer).write(stringArgumentCaptor.capture());

        assertEquals("Your cart is empty", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchIllegalArgumentException() throws ServletException, IOException {
        when(response.getWriter()).thenReturn(writer);
        when(orderService.placeOrder(request, deliveryPrice)).thenThrow(new IllegalArgumentException("Please fill out all fields"));

        servlet.doPost(request, response);

        verify(writer).write(stringArgumentCaptor.capture());

        assertEquals("Please fill out all fields", stringArgumentCaptor.getValue());
    }
}
