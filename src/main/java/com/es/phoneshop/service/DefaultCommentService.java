package com.es.phoneshop.service;

import com.es.phoneshop.model.comment.Comment;
import com.es.phoneshop.model.comment.Rate;
import com.es.phoneshop.model.comment.RateCalculationException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultCommentService implements CommentService {

    private static DefaultCommentService instance;

    private static Map<Integer, Rate> rateMap = new HashMap<>();

    private ProductDao productDao;

    private DefaultCommentService() {
        productDao = ArrayListProductDao.getInstance();
        rateMap.put(1, Rate.ONE);
        rateMap.put(2, Rate.TWO);
        rateMap.put(3, Rate.THREE);
        rateMap.put(4, Rate.FOUR);
        rateMap.put(5, Rate.FIVE);
    }

    public static DefaultCommentService getInstance() {
        if (instance == null) {
            instance = new DefaultCommentService();
        }
        return  instance;
    }

    @Override
    public void addComment(Long productId, int rate, String name, String commentText) {
        Product product = getProduct(productId);
        Comment comment = new Comment(commentText, name, getRate(rate));
        List<Comment> comments = product.getComments();
        if(comments == null) {
            comments = new ArrayList<>();
            product.setComments(comments);
        }
        comments.add(comment);
        product.setAverageRate(Precision.round(averageRate(product), 1));
    }

    private Product getProduct(Long productId) {
        Optional<Product> optionalProduct = productDao.getProduct(productId);
        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException();
        }
        return optionalProduct.get();
    }

    @Override
    public Rate getRate(int rate) {
        Rate r = rateMap.get(rate);
        if (r != null) {
            return r;
        } else {
            throw new RateCalculationException("Wrong rate was received");
        }
    }

    @Override
    public double averageRate(Product product) {
        return product.getComments().stream()
                .map(Comment::getRate)
                .mapToInt(Rate::getValue)
                .average().getAsDouble();
    }
}
