package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ProductDao {
    Optional<Product> getProduct(Long id);
    List<Product> findProducts(Predicate<? super Product> predicate, String q, SortField field, SortOrder order);
    void save(Product product);
    void delete(Long id);
}
