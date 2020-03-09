package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.NegativeNumberException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.exceptions.CartItemNotfoundException;
import com.es.phoneshop.exceptions.OutOfStockException;
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
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCartServiceTest {

    private static final String CART_ATTRIBUTE = "cart_" + DefaultCartService.class;
    private static final long PRODUCT_ID = 1L;

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

    @Test(expected = NegativeNumberException.class)
    public void AddMethodShouldThrowNegativeNumberException() {
        cartService.update(new Cart(), PRODUCT_ID, -1);
    }

    @Test
    public void testGetCard() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CART_ATTRIBUTE)).thenReturn(null);

        cartService.getCart(request);

        verify(session).setAttribute(anyString(), any());
    }

    @Test
    public void testUpdate() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setStock(100);
        product.setId(PRODUCT_ID);
        CartItem item = new CartItem(product, 1);
        cart.getCartItems().add(item);

        cartService.update(cart, PRODUCT_ID, 2);

        assertEquals(2, item.getQuantity());
    }

    @Test(expected = CartItemNotfoundException.class)
    public void shouldThrowCartItemNotfoundException() {
        Cart cart = new Cart();
        cartService.update(cart, PRODUCT_ID, 2);
    }

    @Test(expected = OutOfStockException.class)
    public void shouldThrowOutOfStockExceptionWhenQuantityIsBiggerThanStock() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setStock(1);
        product.setId(PRODUCT_ID);
        CartItem item = new CartItem(product, 1);
        cart.getCartItems().add(item);

        cartService.update(cart, PRODUCT_ID, 100);
    }

    @Test(expected = NegativeNumberException.class)
    public void UpdateMethodShouldThrowNegativeNumberException() {
        cartService.update(new Cart(), PRODUCT_ID, -100);
    }

    @Test
    public void testDelete() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setId(PRODUCT_ID);
        cart.getCartItems().add(new CartItem(product, 1));

        cartService.delete(cart, PRODUCT_ID);

        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    public void testCalculatePrice() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setPrice(new BigDecimal(100));
        cart.getCartItems().add(new CartItem(product, 5));
        cart.getCartItems().add(new CartItem(product, 5));

        cartService.calculateTotalPrice(cart);

        assertEquals(1000, cart.getTotal().intValue());
    }

    @Test
    public void testGetTotalPrice() {
        Cart cart = new Cart();
        cart.setTotal(new BigDecimal(100));
        when(request.getLocale()).thenReturn(Locale.ROOT);

        String price = cartService.getTotalPrice(request, cart);

        assertEquals("USD 100.00", price);
    }
}
