package com.es.phoneshop.model.product;

import com.es.phoneshop.demodata.ProductDemoDataServletContextListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductDaoUtilTest {

    ProductDao productDao;

    @Before
    public void setup(){
        productDao = ArrayListProductDao.INSTANCE;
        ProductDemoDataServletContextListener listener = new ProductDemoDataServletContextListener();
        listener.generateSampleData();
    }

    @Test
    public void shouldReturnFalseWhenAnyMatchesWasNotFound() {
        Product product = productDao.getProduct(1L).get();
        assertEquals(ProductDaoUtil.countMatches(product, "r"), false);
    }

    @Test
    public void shouldSetCorrectNumberOfMatchesWhenWasFoundAnyCoincidences() {
        Product product = productDao.getProduct(1L).get();
        ProductDaoUtil.countMatches(product, "Samsung");
        assertEquals(1, product.getMatches());
    }

    @Test
    public void testProductDaoUtilCountMatches() {
        assertEquals(8, productDao.findProducts(p -> ProductDaoUtil.countMatches(p, "S"), SortField.price, SortOrder.asc).size());
    }
}
