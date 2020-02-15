package com.es.phoneshop.service;

import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

public class OrderService {

    private static OrderService instance;

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

}
