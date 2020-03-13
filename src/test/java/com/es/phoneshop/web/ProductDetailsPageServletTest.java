package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentwatched.RecentWatchedProducts;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.RecentWatchedProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    private static final String PATH_INFO = "/1";

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @InjectMocks
    private ProductDetailsPageServlet servlet;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    private RecentWatchedProductService recentWatchedProductService;
    @Mock
    private PrintWriter writer;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn(PATH_INFO);
        when(productDao.getProduct(1L)).thenReturn(Optional.of(new Product()));
        when(cartService.getCart(request)).thenReturn(new Cart());
        when(recentWatchedProductService.getRecentWatchProducts(request)).thenReturn(new RecentWatchedProducts());
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Product product = new Product();
        Cart cart = new Cart();
        RecentWatchedProducts recentWatchedProducts = new RecentWatchedProducts();

        when(productDao.getProduct(1L)).thenReturn(Optional.of(product));
        when(cartService.getCart(request)).thenReturn(cart);
        when(recentWatchedProductService.getRecentWatchProducts(request)).thenReturn(recentWatchedProducts);

        servlet.doGet(request, response);

        verify(request).setAttribute(Mockito.eq("product"), productArgumentCaptor.capture());
        assertEquals(product, productArgumentCaptor.getValue());
        verify(request).setAttribute(Mockito.eq("cart"), cartArgumentCaptor.capture());
        assertEquals(cart, cartArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(response.getWriter()).thenReturn(writer);
        doNothing().when(writer).write(anyString());
        when(cartService.getCart(request)).thenReturn(new Cart());

        servlet.doPost(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Cart[[]]", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchParseExceptionWhenQuantityIsNotNumber() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("e");
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(response.getWriter()).thenReturn(writer);
        doNothing().when(writer).write(anyString());

        servlet.doPost(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Not a number", stringArgumentCaptor.getValue());
    }

    @Test
    public void shouldCatchOutOfStockExceptionWhenQuantityIsMoreThanStock() throws ServletException, IOException {
        Product product = new Product();
        product.setStock(100);
        Cart cart = new Cart();

        when(request.getParameter("quantity")).thenReturn("1000");
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(cartService.getCart(request)).thenReturn(cart);
        doThrow(new OutOfStockException(product, 100)).when(cartService).add(cart, 1L, 1000);
        when(response.getWriter()).thenReturn(writer);
        doNothing().when(writer).write(anyString());

        servlet.doPost(request, response);

        verify(writer).write(stringArgumentCaptor.capture());
        assertEquals("Not enough stock, available " + product.getStock(), stringArgumentCaptor.getValue());
    }

}
