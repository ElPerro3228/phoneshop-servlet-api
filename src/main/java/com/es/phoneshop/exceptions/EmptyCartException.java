package com.es.phoneshop.exceptions;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException() {
        super();
    }

    public EmptyCartException(String message) {
        super(message);
    }
}
