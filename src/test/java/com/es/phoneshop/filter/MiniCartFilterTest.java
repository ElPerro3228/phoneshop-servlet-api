package com.es.phoneshop.filter;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartFilterTest {

    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private MiniCartFilter filter = new MiniCartFilter();
    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Test
    public void testDoFilter() throws IOException, ServletException {
        when(cartService.getCart(request)).thenReturn(new Cart());
        doNothing().when(filterChain).doFilter(request,response);

        filter.doFilter(request, response, filterChain);

        verify(request).setAttribute(eq("miniCart"), integerArgumentCaptor.capture());
        assertEquals(0, (int) integerArgumentCaptor.getValue());
    }

}
