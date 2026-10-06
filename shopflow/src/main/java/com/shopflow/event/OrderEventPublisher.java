package com.shopflow.event;

import java.util.ArrayList;
import java.util.List;

public class OrderEventPublisher {

    private static final System.Logger LOG = System.getLogger(OrderEventPublisher.class.getName());

    private final List<OrderEventListener> listeners = new ArrayList<>();

    public void subscribe(OrderEventListener listener) {
        listeners.add(listener);
    }

    public void publish(OrderStatusChanged event) {
        for (OrderEventListener listener : listeners) {
            try {
                listener.onStatusChanged(event);
            } catch (RuntimeException e) {
                LOG.log(System.Logger.Level.ERROR, "Listener %s failed for %s".formatted(listener, event), e);
            }
        }
    }
}
