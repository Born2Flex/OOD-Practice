package com.shopflow.model.discount;

import com.shopflow.model.customer.CustomerType;

public record CustomerTypeSpecification(CustomerType type) implements Specification<PricingContext> {

    @Override
    public boolean isSatisfiedBy(PricingContext context) {
        return context.customer().type() == type;
    }
}
