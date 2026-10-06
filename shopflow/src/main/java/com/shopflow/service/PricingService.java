package com.shopflow.service;

import com.shopflow.model.Customer;
import com.shopflow.model.Money;
import com.shopflow.model.Order;
import com.shopflow.model.discount.Discount;
import com.shopflow.model.discount.PricingContext;

public class PricingService {

    private final Discount discount;

    public PricingService(Discount discount) {
        this.discount = discount;
    }

    public Money total(Order order, Customer customer) {
        if (!order.customerId().equals(customer.id())) {
            throw new IllegalArgumentException("Order %s does not belong to customer %s".formatted(order.id(), customer.id()));
        }
        Money subtotal = order.subtotal();
        Money discountAmount = discount.amountFor(new PricingContext(order, customer));
        return subtotal.subtract(discountAmount.min(subtotal));
    }
}
