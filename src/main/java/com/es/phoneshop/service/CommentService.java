package com.es.phoneshop.service;

import com.es.phoneshop.model.comment.Rate;
import com.es.phoneshop.model.product.Product;

public interface CommentService {

    void addComment(Long productId, int rate, String name, String commentText);

    Rate getRate(int rate);

    double averageRate(Product product);
}
