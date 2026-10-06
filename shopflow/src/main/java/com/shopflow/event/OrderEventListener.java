package com.shopflow.event;

@FunctionalInterface
public interface OrderEventListener {

    void onStatusChanged(OrderStatusChanged event);
}
