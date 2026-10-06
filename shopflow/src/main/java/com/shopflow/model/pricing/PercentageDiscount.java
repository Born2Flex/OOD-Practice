package com.shopflow.model.pricing;

import com.shopflow.model.money.Money;
import com.shopflow.model.money.Percentage;

public record PercentageDiscount(Percentage percentage, Specification<PricingContext> condition) implements Discount {

    @Override
    public Money amountFor(PricingContext context) {
        Money subtotal = context.order().subtotal();
        return condition.isSatisfiedBy(context)
                ? subtotal.percentage(percentage)
                : Money.zero(subtotal.currency());
    }
}
