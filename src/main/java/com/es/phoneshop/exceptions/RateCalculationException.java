package com.es.phoneshop.exceptions;

public class RateCalculationException extends RuntimeException {
    public RateCalculationException() {
        super();
    }

    public RateCalculationException(String message) {
        super(message);
    }
}
