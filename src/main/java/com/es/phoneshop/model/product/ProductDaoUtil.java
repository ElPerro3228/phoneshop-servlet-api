package com.es.phoneshop.model.product;

import java.util.Comparator;

public final class ProductDaoUtil {

    public static boolean countMatches(SearchResultEntry p, String query) {
        boolean wasAnyMatches = false;
        p.setCountOfMatches(0);
        String[] splittedQuery = query.toLowerCase().split(" ");
        for (String s : splittedQuery) {
            if (p.getProduct().getDescription().toLowerCase().contains(s)) {
                wasAnyMatches = true;
                p.setCountOfMatches(p.getCountOfMatches() + 1);
            }
        }
        return wasAnyMatches;
    }

    public static Comparator<Product> getComparator(SortField field, SortOrder order) {
        Comparator<Product> comparator;
        if ((field == null) || (order == null)) {
            return new DefaultComparator();
        }
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

    private static class DefaultComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return 0;
        }
    }
}
