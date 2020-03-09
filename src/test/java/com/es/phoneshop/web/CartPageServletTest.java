package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.NegativeNumberException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.CartItemNotfoundException;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;
import org.json.simple.JSONObject;
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
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {

    private static final BigDecimal TOTAL_PRICE = new BigDecimal(100);

    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private PrintWriter writer;
    @InjectMocks
    private CartPageServlet servlet = new CartPageServlet();
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Product product = new Product();
        product.setCurrency(Currency.getInstance("USD"));
        when(cartService.getCart(request)).thenReturn(new Cart());

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(stringArgumentCaptor.capture());
        assertEquals("/WEB-INF/pages/cart.jsp", stringArgumentCaptor.getValue());
    }

    @Test
    public void testDoPut() throws IOException, ServletException {
        Cart cart = new Cart();
        cart.setTotal(TOTAL_PRICE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        jsonObject.put("quantity", 1);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(jsonObject.toString().getBytes()));
        when(response.getWriter()).thenReturn(writer);
        when(cartService.getTotalPrice(request, cart)).thenReturn("100");

        servlet.doPut(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals(TOTAL_PRICE.toString(), stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchParseException() throws IOException, ServletException {
        Cart cart = new Cart();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        jsonObject.put("quantity", "e");

        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream("jsonObject".getBytes()));
        when(response.getWriter()).thenReturn(writer);

        servlet.doPut(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Not a number", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchOutOfStockExceptionWhenQuantityIsMoreThanStock() throws IOException, ServletException {
        Cart cart = new Cart();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        jsonObject.put("quantity", 1);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(jsonObject.toString().getBytes()));
        when(response.getWriter()).thenReturn(writer);
        doThrow(new OutOfStockException("Not enough stock", new Product(), 1)).when(cartService).update(cart, 1L, 1);

        servlet.doPut(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Not enough stock", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchNumberFormatExceptionWhenIdIsWrong() throws IOException, ServletException {
        Cart cart = new Cart();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "number");
        jsonObject.put("quantity", 1);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(jsonObject.toString().getBytes()));
        when(response.getWriter()).thenReturn(writer);

        servlet.doPut(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Something's wrong", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchNegativeNumberException() throws IOException, ServletException {
        Cart cart = new Cart();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        jsonObject.put("quantity", -1);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(jsonObject.toString().getBytes()));
        when(response.getWriter()).thenReturn(writer);
        doThrow(new NegativeNumberException("Negative number")).when(cartService).update(cart, 1L, -1);

        servlet.doPut(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Negative number", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchCartItemNotFoundException() throws IOException, ServletException {
        Cart cart = new Cart();
        cart.setTotal(TOTAL_PRICE);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        jsonObject.put("quantity", 1);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(jsonObject.toString().getBytes()));
        when(response.getWriter()).thenReturn(writer);
        doThrow(new CartItemNotfoundException()).when(cartService).update(cart, 1L, 1);

        servlet.doPut(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Cart item was deleted", stringArgumentCaptor.getValue());
    }

    @Test
    public void testDoDelete() throws IOException, ServletException {
        Cart cart = new Cart();
        cart.setTotal(TOTAL_PRICE);
        when(cartService.getCart(request)).thenReturn(cart);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(jsonObject.toString().getBytes()));
        when(response.getWriter()).thenReturn(writer);
        when(cartService.getTotalPrice(request, cart)).thenReturn("100");

        servlet.doDelete(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals(TOTAL_PRICE.toString(), stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchParseExceptionWhenIdIsNotANumber() throws IOException, ServletException {
        Cart cart = new Cart();
        when(cartService.getCart(request)).thenReturn(cart);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1L);
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream("jsonObject".getBytes()));
        when(response.getWriter()).thenReturn(writer);

        servlet.doDelete(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Something's wrong", stringArgumentCaptor.getValue());
    }

}
