package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class ProductDaoUtilTest {

    private Map<Date, BigDecimal> priceHistory;

    @Before
    public void setup(){
        priceHistory = new TreeMap<>();
    }

    @Test
    public void shouldReturnFalseWhenAnyMatchesWasNotFound() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "h", priceHistory);
        assertFalse(ProductDaoUtil.countMatches(product, "r"));
    }

    @Test
    public void shouldSetCorrectNumberOfMatchesWhenWasFoundAnyCoincidences() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "h", priceHistory);
        ProductDaoUtil.countMatches(product, "Samsung");
        assertEquals(1, product.getMatches());
    }
}
