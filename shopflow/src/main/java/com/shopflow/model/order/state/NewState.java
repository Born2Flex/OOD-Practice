package com.shopflow.model.order.state;

import com.shopflow.model.order.OrderStatus;

public final class NewState implements OrderState {

    @Override
    public OrderStatus status() {
        return OrderStatus.NEW;
    }

    @Override
    public boolean canPay() {
        return true;
    }

    @Override
    public OrderState pay() {
        return new PaidState();
    }

    @Override
    public OrderState cancel() {
        return new CancelledState();
    }
}
