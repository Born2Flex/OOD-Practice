package com.shopflow.model.state;

import com.shopflow.model.OrderStatus;

public final class CancelledState implements OrderState {

    @Override
    public OrderStatus status() {
        return OrderStatus.CANCELLED;
    }
}
