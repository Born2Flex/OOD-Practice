package com.shopflow.event;

import java.util.ArrayList;
import java.util.List;

public class OrderEventPublisher {

    private final List<OrderEventListener> listeners = new ArrayList<>();

    public void subscribe(OrderEventListener listener) {
        listeners.add(listener);
    }

    public void publish(OrderStatusChanged event) {
        listeners.forEach(listener -> listener.onStatusChanged(event));
    }
}
