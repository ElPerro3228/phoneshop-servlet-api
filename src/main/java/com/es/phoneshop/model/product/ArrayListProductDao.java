package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private  ArrayList<Product> products = new ArrayList<>();
    private long maxId = 1L;

    @Override
    public synchronized Product getProduct(Long id) {
        if(id < maxId && id > 0) {
            return products.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }


    @Override
    public synchronized List<Product> findProducts(Predicate<? super Product> predicate) {
        return products.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if(product != null){
            if( products.stream().anyMatch(p->p.getId().equals(product.getId())) ) {
                products = (ArrayList<Product>) products.stream().map(p -> p.getId().equals(product.getId()) ? product : p).collect(Collectors.toList());
            } else{
                products.add(product);
                maxId++;
            }
        }
    }

    @Override
    public synchronized void delete(Long id) {
        if(id < maxId && id > 0){
            products.removeIf(p -> p.getId().equals(id));
        }
    }
}
