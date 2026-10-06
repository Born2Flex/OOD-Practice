package com.shopflow.model.state;

import com.shopflow.model.OrderStatus;

public final class PaidState implements OrderState {

    @Override
    public OrderStatus status() {
        return OrderStatus.PAID;
    }

    @Override
    public OrderState ship() {
        return new ShippedState();
    }

    @Override
    public OrderState cancel() {
        return new CancelledState();
    }
}
