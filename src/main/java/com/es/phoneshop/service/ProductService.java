package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> createProductList(String query, SortOrder sortOrder, SortField sortField);
    List<Product> createProductList(String query, String maxPrice, String minPrice, String searchType);

}
