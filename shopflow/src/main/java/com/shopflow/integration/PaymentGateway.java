package com.shopflow.integration;

import com.shopflow.model.Money;
import com.shopflow.model.TransactionId;

public interface PaymentGateway {

    TransactionId charge(Money amount);
}
