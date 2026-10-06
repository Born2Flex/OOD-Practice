package com.shopflow.exception;

import com.shopflow.model.OrderStatus;

public class IllegalOrderTransitionException extends RuntimeException {

    public IllegalOrderTransitionException(OrderStatus from, String action) {
        super("Cannot %s an order in status %s".formatted(action, from));
    }
}
