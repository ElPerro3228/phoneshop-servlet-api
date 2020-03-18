package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class ProductUtilTest {

    private Map<Date, BigDecimal> priceHistory;

    @Before
    public void setup(){
        priceHistory = new TreeMap<>();
    }

    @Test
    public void shouldReturnZeroWhenAnyMatchesWasNotFound() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "h", priceHistory);
        assertEquals(0, ProductUtil.countMatches(product, "r"));
    }

    @Test
    public void shouldSetCorrectNumberOfMatchesWhenWasFoundAnyCoincidences() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "h", priceHistory);
        assertEquals(1, ProductUtil.countMatches(product, "Samsung"));
    }
}
