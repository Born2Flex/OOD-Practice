package com.shopflow.model.discount;

import com.shopflow.model.money.Money;

public interface Discount {

    Money amountFor(PricingContext context);
}
