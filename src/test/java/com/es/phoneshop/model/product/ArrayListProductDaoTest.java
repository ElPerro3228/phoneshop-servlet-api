package com.es.phoneshop.model.product;

import com.es.phoneshop.demodata.ProductDemoDataServletContextListener;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{

    private ArrayListProductDao productDao;
    private Currency usd;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.INSTANCE;
        usd = Currency.getInstance("USD");
        productDao.generateTestData();
    }

    @Test
    public void shouldNotFindAnyProductWhenIdEqualsZero() {
        assertTrue(productDao.findProducts(product -> product.getId().equals(0L), SortField.price, SortOrder.asc).isEmpty());
    }

    @Test
    public void shouldFindOneProductWhenIdEqualsOne() {
        assertEquals(1, productDao.findProducts(p -> p.getId().equals(1L), SortField.price, SortOrder.asc).size());
    }

    @Test
    public void shouldChangeProductStateWhenProductIsInArrayList() {
        Product product = new Product(1L, "sgs", "S", new BigDecimal(100), usd, 100, "");
        productDao.save(product);
        assertTrue(productDao.findProducts(p -> p.getDescription().equals("S"), SortField.price, SortOrder.asc).size() == 1);
    }


    @Test
    public void shouldAddProductWithNoExistingId() {
        int startSize = productDao.findProducts(p->true, SortField.price, SortOrder.asc).size();
        productDao.save(new Product (100L, "", "Samsung ", new BigDecimal(1), usd, 1, ""));
        int endSize = productDao.findProducts(p->true, SortField.price, SortOrder.asc).size();
        assertTrue(startSize < endSize);
    }

    @Test
    public void shouldGetProductWithExistingId() {
        Product p = new Product (1L, "", "Samsung ", new BigDecimal(1), usd, 1, "");
        assertEquals(p.getId(), productDao.getProduct(1L).get().getId());
    }

    @Test
    public void shouldDeleteProductWithExistingId() {
        int startSize = productDao.findProducts(p -> true, SortField.price, SortOrder.asc).size();
        productDao.delete(8L);
        int endSize = productDao.findProducts(p->true, SortField.price, SortOrder.asc).size();
        assertTrue(startSize > endSize);
    }

    @Test
    public void shouldDeleteCorrectProductWithExistingId() {
        Product product = productDao.getProduct(3L).get();
        productDao.delete(product.getId());
        assertEquals(0, productDao.findProducts(p->p.getId().equals(product.getId()), SortField.price, SortOrder.asc).size() );
    }
}
