package com.es.phoneshop.model.product;

import java.util.Comparator;
import java.util.Currency;

public final class ProductUtil {

    public static final Currency CURRENCY = Currency.getInstance("USD");

    public static int countMatches(Product p, String query, boolean flag) {
        int countMatches = 0;
        String[] splittedQuery;
        if (flag == true) {
            splittedQuery = query.toLowerCase().split(" ");
            for (String s : splittedQuery) {
                if (p.getDescription().toLowerCase().contains(s)) {
                    countMatches++;
                }
            }
        } else {
            if (p.getDescription().toLowerCase().contains(query.toLowerCase())) {
                countMatches++;
            }
        }
        return countMatches;
    }

    public static Comparator<Product> getComparator(SortField field, SortOrder order) {
        Comparator<Product> comparator;
        if (field == SortField.price) {
            comparator = Comparator.comparing(Product::getPrice).reversed();
        } else {
            comparator = Comparator.comparing(Product::getDescription);
        }
        if (order == SortOrder.desc) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
