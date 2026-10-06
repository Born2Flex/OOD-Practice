package com.shopflow.model.discount;

import com.shopflow.model.Money;

public interface Discount {

    Money amountFor(PricingContext context);
}
