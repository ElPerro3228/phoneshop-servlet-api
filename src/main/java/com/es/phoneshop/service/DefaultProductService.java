package com.es.phoneshop.service;

import com.es.phoneshop.model.product.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductService implements ProductService {

    private static DefaultProductService instance;

    private ProductDao productDao;

    private DefaultProductService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static DefaultProductService getInstance() {
        if (instance == null) {
            instance = new DefaultProductService();
        }
        return instance;
    }

    public List<Product> createProductList(String query, SortOrder sortOrder, SortField sortField) {
        if (query == null) {
            query = "";
        }
        List<SearchResultEntry> entries = productDao.findProducts(query);
        if (sortField == null || sortOrder == null) {
            return entries.stream()
                    .sorted(Comparator.comparingInt(SearchResultEntry::getCountOfMatches).reversed())
                    .map(SearchResultEntry::getProduct)
                    .collect(Collectors.toList());
        }
        return entries.stream()
                .map(SearchResultEntry::getProduct)
                .sorted(ProductDaoUtil.getComparator(sortField, sortOrder))
                .collect(Collectors.toList());
    }
}
