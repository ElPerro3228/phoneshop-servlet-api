package com.es.phoneshop.service;

import com.es.phoneshop.model.product.*;

import java.math.BigDecimal;
import java.util.Collections;
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

    @Override
    public List<Product> createProductList(String query, SortOrder sortOrder, SortField sortField) {
        if (query == null) {
            query = "";
        }
        List<SearchResultEntry> entries = productDao.findProducts(query, true);
        if (sortField == null || sortOrder == null) {
            return entries.stream()
                    .sorted(Comparator.comparingInt(SearchResultEntry::getCountOfMatches).reversed())
                    .map(SearchResultEntry::getProduct)
                    .collect(Collectors.toList());
        }
        return entries.stream()
                .map(SearchResultEntry::getProduct)
                .sorted(ProductUtil.getComparator(sortField, sortOrder))
                .collect(Collectors.toList());
    }

    public List<Product> createProductList(String query, String maxPrice, String minPrice, String searchType) {
        if (query == null) {
            return Collections.emptyList();
        }
        if ((maxPrice == null) || ("".equals(maxPrice))) {
            maxPrice = Double.toString(Double.POSITIVE_INFINITY);
        }
        if (minPrice == null || ("".equals(minPrice))) {
            minPrice = "0";
        }
        if ((searchType == null) || ("".equals(searchType))) {
            searchType = "any";
        }
        double minPriceDoubleValue = Double.parseDouble(minPrice);
        double maxPriceDoubleValue = Double.parseDouble(maxPrice);
        List<SearchResultEntry> entries;
        if (searchType.equals("any")) {
            entries = productDao.findProducts(query, true);
        } else {
            entries = productDao.findProducts(query, false);
        }
        return entries.stream()
                .map(SearchResultEntry::getProduct)
                .filter(product -> product.getPrice().doubleValue() >= minPriceDoubleValue)
                .filter(product -> product.getPrice().doubleValue() <= maxPriceDoubleValue)
                .collect(Collectors.toList());
    }
}
