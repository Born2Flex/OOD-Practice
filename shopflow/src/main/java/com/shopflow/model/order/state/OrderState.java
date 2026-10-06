package com.shopflow.model.order.state;

import com.shopflow.model.order.OrderStatus;
import com.shopflow.model.order.exception.IllegalOrderTransitionException;

public interface OrderState {

    OrderStatus status();

    default boolean canPay() {
        return false;
    }

    default OrderState pay() {
        throw new IllegalOrderTransitionException(status(), "pay");
    }

    default OrderState ship() {
        throw new IllegalOrderTransitionException(status(), "ship");
    }

    default OrderState deliver() {
        throw new IllegalOrderTransitionException(status(), "deliver");
    }

    default OrderState cancel() {
        throw new IllegalOrderTransitionException(status(), "cancel");
    }
}
