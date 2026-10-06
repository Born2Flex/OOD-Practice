package com.shopflow.integration;

import com.shopflow.model.money.Money;
import com.shopflow.model.payment.TransactionId;

public interface PaymentGateway {

    TransactionId charge(Money amount);
}
