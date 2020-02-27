package com.es.phoneshop.service;

import com.es.phoneshop.model.comment.Comment;
import com.es.phoneshop.model.comment.Rate;
import com.es.phoneshop.model.comment.RateCalculationException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCommentServiceTest {
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private CommentService commentService = DefaultCommentService.getInstance();

    @Test
    public void shouldSetNewArrayListIfProductHasNoComments() {
        Optional<Product> optionalProduct = Optional.of(new Product());
        when(productDao.getProduct(anyLong())).thenReturn(optionalProduct);
        assertNull(optionalProduct.get().getComments());
        commentService.addComment(1L, 4, "name", "comment");
        assertNotNull(optionalProduct.get().getComments());
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowProductNotFoundException() {
        when(productDao.getProduct(anyLong())).thenReturn(Optional.empty());
        commentService.addComment(1L, 4, "name", "comment");
    }

    @Test
    public void shouldReturnCorrectRateObject() {
        Rate rate = commentService.getRate(5);
        assertEquals(Rate.FIVE, rate);
    }

    @Test(expected = RateCalculationException.class)
    public void shouldThrowRateCalculationException() {
        commentService.getRate(6);
    }

    @Test
    public void testAverageRate() {
        Product product = Mockito.mock(Product.class);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("a", "a", Rate.FIVE));
        comments.add(new Comment("a", "a", Rate.THREE));
        when(product.getComments()).thenReturn(comments);
        assertEquals(4, commentService.averageRate(product), 0.0);
    }
}
