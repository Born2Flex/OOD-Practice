package com.shopflow.model.payment;

import com.shopflow.model.money.Money;

public interface PaymentGateway {

    TransactionId charge(Money amount);
}
