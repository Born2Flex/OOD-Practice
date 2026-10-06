package com.shopflow.model.pricing;

import com.shopflow.model.money.Money;

public interface Discount {

    Money amountFor(PricingContext context);
}
