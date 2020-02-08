package com.es.phoneshop.model.product;

import java.util.Comparator;

public class ProductDaoUtil {

    public static boolean countMatches(Product p, String query) {
        boolean wasAnyMatches = false;
        p.setMatches(0);
        String[] splittedQuery = query.toLowerCase().split(" ");
        for (String s : splittedQuery) {
            if (p.getDescription().toLowerCase().contains(s)) {
                wasAnyMatches = true;
                p.setMatches(p.getMatches() + 1);
            }
        }
        return wasAnyMatches;
    }

    public Comparator<Product> getComparator(SortField field, SortOrder order) {
        if ((field == null) || (order == null)) {
            return new DefaultComparator();
        }
        if (field == SortField.price) {
            return order == SortOrder.asc ? new ProductAscPriceComparator() : new ProductDescPriceComparator();
        } else {
            return order == SortOrder.asc ? new ProductAscDescriptionComparator() : new ProductDescDescriptionComparator();
        }
    }

    private class ProductAscDescriptionComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    }

    private class ProductDescDescriptionComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o2.getDescription().compareTo(o1.getDescription());
        }
    }

    private class ProductAscPriceComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o2.getPrice().compareTo(o1.getPrice());
        }
    }

    private class ProductDescPriceComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    }

    private class DefaultComparator implements Comparator<Product> {
        @Override
        public int compare(Product o1, Product o2) {
            return 0;
        }
    }
}
