package com.shopflow.model.pricing;

import com.shopflow.model.money.Money;

import java.util.List;

public record StackedDiscount(List<Discount> discounts) implements Discount {

    public StackedDiscount {
        discounts = List.copyOf(discounts);
    }

    public static StackedDiscount of(Discount... discounts) {
        return new StackedDiscount(List.of(discounts));
    }

    @Override
    public Money amountFor(PricingContext context) {
        Money total = Money.zero(context.order().subtotal().currency());
        for (Discount discount : discounts) {
            total = total.add(discount.amountFor(context));
        }
        return total;
    }
}
