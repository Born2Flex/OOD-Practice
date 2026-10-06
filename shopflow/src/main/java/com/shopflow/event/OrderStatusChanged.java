package com.shopflow.event;

import com.shopflow.model.order.OrderStatus;

import java.util.UUID;

public record OrderStatusChanged(UUID orderId, UUID customerId, OrderStatus from, OrderStatus to) {
}
