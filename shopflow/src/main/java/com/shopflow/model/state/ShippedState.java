package com.shopflow.model.state;

import com.shopflow.model.order.OrderStatus;

public final class ShippedState implements OrderState {

    @Override
    public OrderStatus status() {
        return OrderStatus.SHIPPED;
    }

    @Override
    public OrderState deliver() {
        return new DeliveredState();
    }
}
