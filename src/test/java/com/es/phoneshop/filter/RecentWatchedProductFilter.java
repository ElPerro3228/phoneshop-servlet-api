package com.es.phoneshop.filter;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recentwatched.RecentWatchedProducts;
import com.es.phoneshop.service.RecentWatchedProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecentWatchedProductFilter {

    @InjectMocks
    private RecentWatchedProductsFilter recentWatchedProductsFilter = new RecentWatchedProductsFilter();

    @Mock
    private RecentWatchedProductService recentWatchedProductService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private RecentWatchedProducts recentWatchedProducts;
    @Captor
    private ArgumentCaptor<List<Product>> captor;

    @Test
    public void testDoFilter() throws IOException, ServletException {
        List<Product> products = Collections.emptyList();

        when(recentWatchedProductService.getRecentWatchProducts(request)).thenReturn(recentWatchedProducts);
        when(recentWatchedProducts.getProducts()).thenReturn(products);

        recentWatchedProductsFilter.doFilter(request, response, filterChain);

        verify(request).setAttribute(eq("resentWatched"), captor.capture());
        assertEquals(products, captor.getValue());
    }

}
