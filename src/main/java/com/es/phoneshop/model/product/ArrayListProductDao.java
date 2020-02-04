package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private  List<Product> products = new ArrayList<>();
    private long maxId = 1L;

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
    public synchronized List<Product> findProducts(Predicate<? super Product> predicate) {
        return products.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if ((product != null) && (product.getId() != null)) {
            if(products.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
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
            } else {
                products.add(product);
                maxId++;
            }
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if ((id < maxId) && (id > 0)) {
            products.removeIf(p -> p.getId().equals(id));
        }
    }

}
