package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartServiceTest {

    private static final String CART_ATTRIBUTE = "cart_" + DefaultCartService.class;

    @Mock
    private ProductDao productDao;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @InjectMocks
    private CartService cartService = DefaultCartService.getInstance();
    private Currency usd;
    private Map<Date, BigDecimal> priceHistory;

    @Before
    public void setup() {
        usd = Currency.getInstance("USD");
        priceHistory = new TreeMap<>();
    }

    @Test
    public void testAddMethod() {
        Product product = new Product(1L, "sgs", "S", new BigDecimal(100), usd, 100, "", priceHistory);
        Cart cart = new Cart();
        when(productDao.getProduct(anyLong())).thenReturn(Optional.of(product));

        int startSize = cart.getCartItems().size();
        cartService.add(cart, product.getId(), 1);
        int endSize = cart.getCartItems().size();

        int startQuantity = cart.getCartItems().get(0).getQuantity();
        cartService.add(cart, product.getId(), 3);
        int endQuantity = cart.getCartItems().get(0).getQuantity();

        assertTrue(endSize > startSize);
        assertTrue(endQuantity > startQuantity);
    }

    @Test
    public void testGetCard() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CART_ATTRIBUTE)).thenReturn(null);

        cartService.getCart(request);

        verify(session).setAttribute(anyString(), any());
    }
}
