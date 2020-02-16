package com.es.phoneshop.service;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.util.List;

public class OrderService {

    private static OrderService instance;
    private ProductDao productDao;

    private OrderService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public SortOrder getSortOrder(String order) {
        return order == null ? null : order.equals("asc") ? SortOrder.asc : SortOrder.desc;
    }

    public SortField getSortField(String field) {
        return field == null ? null : field.equals("price") ? SortField.price : SortField.description;
    }

    public List<Product> createProductList(String query, SortOrder sortOrder, SortField sortField) {
        if (query == null || "".equals(query)) {
            return productDao.findProducts(p -> ((p.getPrice().doubleValue() > 0) && (p.getStock() > 0)), "", sortField, sortOrder);
        }
        return productDao.findProducts(p -> true, query, sortField, sortOrder);
    }

}
