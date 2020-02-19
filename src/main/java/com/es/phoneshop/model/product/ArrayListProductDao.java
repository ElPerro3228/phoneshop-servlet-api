package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public synchronized List<Product> findProducts(Predicate<? super Product> predicate, String q, SortField field, SortOrder order) {
        return products.stream()
                .filter(p -> p.getPrice() != null)
                .filter(p -> p.getPrice().doubleValue() > 0)
                .filter(p -> p.getStock() > 0)
                .filter(predicate)
                .map(p -> new SearchResultEntry(p, 0))
                .filter(s -> ProductDaoUtil.countMatches(s, q))
                .sorted(Comparator.comparingInt(SearchResultEntry::getCountOfMatches).reversed())
                .map(SearchResultEntry::getProduct)
                .sorted(ProductDaoUtil.getComparator(field, order))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product.getId() != null) {
            Optional<Product> found = getProduct(product.getId());
            if (found.isPresent()) {
                update(found.get(), product);
            } else {
                addProduct(product);
            }
        }
    }

    private void addProduct(Product product) {
        product.setId(maxId);
        products.add(product);
        maxId++;
    }

    private void update(Product oldProduct, Product newProduct) {
        oldProduct.setId(newProduct.getId());
        oldProduct.setCode(newProduct.getCode());
        oldProduct.setCurrency(newProduct.getCurrency());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setImageUrl(newProduct.getImageUrl());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setStock(newProduct.getStock());
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}
