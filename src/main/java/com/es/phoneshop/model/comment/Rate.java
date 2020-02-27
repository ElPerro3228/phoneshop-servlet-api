package com.es.phoneshop.model.comment;

public enum Rate {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    int value;

    Rate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
