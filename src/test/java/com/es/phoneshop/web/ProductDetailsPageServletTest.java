package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    private static final String PATH_INFO = "/1";

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

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

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Product product = new Product();

        when(request.getPathInfo()).thenReturn(PATH_INFO);
        when(productDao.getProduct(1L)).thenReturn(Optional.of(product));

        servlet.doGet(request, response);

        verify(request).setAttribute(Mockito.eq("product"), productArgumentCaptor.capture());
        assertEquals(product, productArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

}
