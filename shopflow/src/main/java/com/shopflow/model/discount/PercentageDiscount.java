package com.shopflow.model.discount;

import com.shopflow.model.Money;
import com.shopflow.model.Percentage;

public record PercentageDiscount(Percentage percentage, Specification<PricingContext> condition) implements Discount {

    @Override
    public Money amountFor(PricingContext context) {
        Money subtotal = context.order().subtotal();
        return condition.isSatisfiedBy(context)
                ? subtotal.percentage(percentage)
                : Money.zero(subtotal.currency());
    }
}
