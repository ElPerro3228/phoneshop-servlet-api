package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;
import com.es.phoneshop.service.OrderService;
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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {

    @InjectMocks
    private ProductListPageServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private OrderService orderService;
    @Captor
    private ArgumentCaptor<List<Product>> productsArgumentCaptor;

    @Before
    public void setup(){
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        List<Product> products = Collections.emptyList();
        SortField sortField = SortField.price;
        SortOrder sortOrder = SortOrder.desc;

        when(orderService.getSortOrder(any())).thenReturn(sortOrder);
        when(orderService.getSortField(any())).thenReturn(sortField);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("products"), productsArgumentCaptor.capture());
        assertEquals(products, productsArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }
}