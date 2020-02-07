package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum ArrayListProductDao implements ProductDao {
    INSTANCE;
    private List<Product> products = new ArrayList<>();
    private long maxId = 1L;

    ArrayListProductDao() {
    }


    @Override
    public synchronized Optional<Product> getProduct(Long id) {
        if ((id < maxId) && (id > 0)) {
            return products.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public synchronized List<Product> findProducts(Predicate<? super Product> predicate, SortField field, SortOrder order) {
        ProductDaoUtil util = new ProductDaoUtil();
        return products.stream()
                .filter(p -> p.getPrice() != null)
                .filter(p -> p.getPrice().doubleValue() > 0)
                .filter(p -> p.getStock() > 0)
                .filter(predicate)
                .sorted(Comparator.comparingInt(Product::getMatches).reversed())
                .sorted(util.getComparator(field, order))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product != null) {
            if(products.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
                update(product);
            } else {
                product.setId(maxId);
                products.add(product);
                maxId++;
            }
        }
    }

    private void update(Product product) {
        products.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .forEach(p -> {
                    p.setId(product.getId());
                    p.setCode(product.getCode());
                    p.setCurrency(product.getCurrency());
                    p.setDescription(product.getDescription());
                    p.setImageUrl(product.getImageUrl());
                    p.setPrice(product.getPrice());
                    p.setStock(product.getStock());
                });
    }

    @Override
    public synchronized void delete(Long id) {
        if ((id < maxId) && (id > 0)) {
            products.removeIf(p -> p.getId().equals(id));
        }
    }
}
