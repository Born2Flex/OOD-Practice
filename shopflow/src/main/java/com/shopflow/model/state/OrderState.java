package com.shopflow.model.state;

import com.shopflow.exception.IllegalOrderTransitionException;
import com.shopflow.model.order.OrderStatus;

public interface OrderState {

    OrderStatus status();

    default boolean canPay() {
        return false;
    }

    default OrderState pay() {
        throw new IllegalOrderTransitionException(status().name(), "pay");
    }

    default OrderState ship() {
        throw new IllegalOrderTransitionException(status().name(), "ship");
    }

    default OrderState deliver() {
        throw new IllegalOrderTransitionException(status().name(), "deliver");
    }

    default OrderState cancel() {
        throw new IllegalOrderTransitionException(status().name(), "cancel");
    }
}
