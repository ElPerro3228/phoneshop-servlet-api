package com.es.phoneshop.service;

import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

public final class SortOrderService {

    public static SortOrder getSortOrder(String order) {
        return order == null ? null : order.equals("asc") ? SortOrder.asc : SortOrder.desc;
    }

    public static SortField getSortField(String field) {
        return field == null ? null : field.equals("price") ? SortField.price : SortField.description;
    }

}
