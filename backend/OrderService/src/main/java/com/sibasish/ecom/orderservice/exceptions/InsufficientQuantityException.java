package com.sibasish.ecom.orderservice.exceptions;

public class InsufficientQuantityException extends RuntimeException {

    private final String productName;
    private final Integer unitsLeft;
    private final Integer unitsRequested;
    public InsufficientQuantityException(String message, String productName, Integer unitsLeft, Integer unitsRequested) {
        super(message);

        this.productName = productName;
        this.unitsLeft = unitsLeft;
        this.unitsRequested = unitsRequested;
    }

    @Override
    public String getMessage() {
        return super.getMessage()
                + " Product Name: " + this.productName
                + ", Units Left: " + this.unitsLeft
                + ", Units Requested: " + this.unitsRequested;
    }
}
