package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao instance;

    private List<Product> products;
    private long maxId;

    private ArrayListProductDao() {
        products = new ArrayList<>();
        maxId = 1L;
    }

    public static ArrayListProductDao getInstance(){
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
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
    public synchronized List<Product> findProducts(Predicate<? super Product> predicate, String q, SortField field, SortOrder order) {
        return products.stream()
                .filter(p -> p.getPrice() != null)
                .filter(p -> p.getPrice().doubleValue() > 0)
                .filter(p -> p.getStock() > 0)
                .filter(predicate)
                .map(p -> new SearchResultEntry(p, 0))
                .filter(s -> ProductDaoUtil.countMatches(s.getProduct(), q))
                .sorted(Comparator.comparingInt(SearchResultEntry::getCountOfMatches).reversed())
                .map(SearchResultEntry::getProduct)
                .sorted(ProductDaoUtil.getComparator(field, order))
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
