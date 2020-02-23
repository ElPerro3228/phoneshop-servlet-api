package com.es.phoneshop.service;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoUtil;
import com.es.phoneshop.model.product.SearchResultEntry;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private static ProductService instance;

    private ProductDao productDao;

    private ProductService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
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

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

}
