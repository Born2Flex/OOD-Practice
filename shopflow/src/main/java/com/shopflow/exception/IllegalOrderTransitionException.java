package com.shopflow.exception;

public class IllegalOrderTransitionException extends RuntimeException {

    public IllegalOrderTransitionException(String status, String action) {
        super("Cannot %s an order in status %s".formatted(action, status));
    }
}
