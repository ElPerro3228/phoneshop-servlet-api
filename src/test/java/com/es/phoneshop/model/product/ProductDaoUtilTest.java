package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
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
        SearchResultEntry s = new SearchResultEntry(product, 0);
        assertFalse(ProductDaoUtil.countMatches(s, "r"));
    }

    @Test
    public void shouldSetCorrectNumberOfMatchesWhenWasFoundAnyCoincidences() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "h", priceHistory);
        SearchResultEntry s = new SearchResultEntry(product, 0);
        ProductDaoUtil.countMatches(s, "Samsung");
        assertEquals(1, s.getCountOfMatches());
    }
}
