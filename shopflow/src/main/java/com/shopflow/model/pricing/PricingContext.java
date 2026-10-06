package com.shopflow.model.pricing;

import com.shopflow.model.customer.Customer;
import com.shopflow.model.order.Order;

public record PricingContext(Order order, Customer customer) {
}
