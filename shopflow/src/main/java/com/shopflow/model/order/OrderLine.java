package com.shopflow.model.order;

import com.shopflow.model.money.Money;

public record OrderLine(Money unitPrice, int quantity) {

    public OrderLine {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        }
    }

    public Money total() {
        return unitPrice.times(quantity);
    }
}
