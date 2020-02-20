package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SearchResultEntry;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProductServiceTest {

    @Mock
    private ProductDao productDao;
    @InjectMocks
    private ProductService defaultProductService = DefaultProductService.getInstance();

    private Currency usd;
    private Map<Date, BigDecimal> priceHistory;

    @Before
    public void setup() {
        usd = Currency.getInstance("USD");
        priceHistory = new TreeMap<>();
    }

    @Test
    public void shouldReturnNotEmptyListAndSortInCorrectOrderWhenThereIsMatchesAndSortParametersWasSent() {
        List<SearchResultEntry> entries = new ArrayList<>();
        Product product1 = new Product(1L, "A", "A", new BigDecimal(50), Currency.getInstance("USD"), 100, "", priceHistory);
        Product product2 = new Product(2L, "B", "B", new BigDecimal(100), Currency.getInstance("USD"), 100, "", priceHistory);
        entries.add(new SearchResultEntry(product1, 1));
        entries.add(new SearchResultEntry(product2, 1));

        when(productDao.findProducts(eq("A B"))).thenReturn(entries);

        List<Product> products = defaultProductService.createProductList("A B", SortOrder.asc, SortField.price);

        assertFalse(products.isEmpty());
        assertEquals(2L, (long)products.get(0).getId());
    }

    @Test
    public void shouldReturnNotEmptyListAndSortWithEntriesCountWhenSortParametersIsNull() {
        List<SearchResultEntry> entries = new ArrayList<>();
        Product product1 = new Product(1L, "A", "A", new BigDecimal(50), Currency.getInstance("USD"), 100, "", priceHistory);
        Product product2 = new Product(2L, "B", "A B", new BigDecimal(100), Currency.getInstance("USD"), 100, "", priceHistory);
        entries.add(new SearchResultEntry(product1, 1));
        entries.add(new SearchResultEntry(product2, 2));

        when(productDao.findProducts(eq("A B"))).thenReturn(entries);

        List<Product> products = defaultProductService.createProductList("A B", null, null);

        assertFalse(products.isEmpty());
        assertEquals(2L, (long)products.get(0).getId());
    }

}
