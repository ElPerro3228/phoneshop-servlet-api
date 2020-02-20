package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recentwatched.RecentWatchedProducts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecentWatchedProductServiceTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private RecentWatchedProductService recentWatchedProductService;
    private RecentWatchedProducts recentWatchedProducts;
    private Currency usd;
    private Map<Date, BigDecimal> priceHistory;

    @Before
    public void setup() {
        recentWatchedProductService = RecentWatchedProductService.getInstance();
        recentWatchedProducts = new RecentWatchedProducts();
        usd = Currency.getInstance("USD");
        priceHistory = new TreeMap<>();
    }

    @Test
    public void testAddProduct() {
        Product product = new Product(1L, "sgs", "S", new BigDecimal(100), usd, 100, "", priceHistory);
        Product product2 = new Product(2L, "sgs", "S", new BigDecimal(100), usd, 100, "", priceHistory);
        Product product3 = new Product(3L, "sgs", "S", new BigDecimal(100), usd, 100, "", priceHistory);
        recentWatchedProductService.addProduct(recentWatchedProducts, product);
        recentWatchedProductService.addProduct(recentWatchedProducts, product2);
        recentWatchedProductService.addProduct(recentWatchedProducts, product3);
        recentWatchedProductService.addProduct(recentWatchedProducts, product);
        assertEquals(3, recentWatchedProducts.getProducts().size());
    }

    @Test
    public void testGetRecentWatchedProducts() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("recentWatchedProducts")).thenReturn(null);

        recentWatchedProductService.getRecentWatchProducts(request);

        verify(session).setAttribute(eq("recentWatchedProducts"), any());
    }
}
