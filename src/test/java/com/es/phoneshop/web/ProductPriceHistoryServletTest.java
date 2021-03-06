package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentwatched.RecentWatchedProducts;
import com.es.phoneshop.service.RecentWatchedProductService;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceHistoryServletTest {

    private static final String PATH_INFO = "/1";
    @InjectMocks
    private ProductPriceHistoryServlet servlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private RecentWatchedProductService recentWatchedProductService;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Product product = new Product();
        RecentWatchedProducts recentWatchedProducts = new RecentWatchedProducts();

        when(request.getPathInfo()).thenReturn(PATH_INFO);
        when(productDao.getProduct(1L)).thenReturn(Optional.of(product));
        when(recentWatchedProductService.getRecentWatchProducts(request)).thenReturn(recentWatchedProducts);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("product"), productArgumentCaptor.capture());
        assertEquals(product, productArgumentCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }
}
