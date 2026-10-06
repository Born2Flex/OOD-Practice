package com.shopflow.model.discount;

import com.shopflow.model.Customer;
import com.shopflow.model.Order;

public record PricingContext(Order order, Customer customer) {
}
