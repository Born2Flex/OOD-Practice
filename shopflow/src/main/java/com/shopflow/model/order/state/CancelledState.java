package com.shopflow.model.order.state;

import com.shopflow.model.order.OrderStatus;

public final class CancelledState implements OrderState {

    @Override
    public OrderStatus status() {
        return OrderStatus.CANCELLED;
    }
}
