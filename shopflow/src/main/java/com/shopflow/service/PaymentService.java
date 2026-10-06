package com.shopflow.service;

import com.shopflow.exception.IllegalOrderTransitionException;
import com.shopflow.integration.PaymentGatewayFactory;
import com.shopflow.model.customer.Customer;
import com.shopflow.model.money.Money;
import com.shopflow.model.order.Order;
import com.shopflow.model.payment.TransactionId;

public class PaymentService {

    private final PricingService pricingService;
    private final PaymentGatewayFactory gatewayFactory;

    public PaymentService(PricingService pricingService, PaymentGatewayFactory gatewayFactory) {
        this.pricingService = pricingService;
        this.gatewayFactory = gatewayFactory;
    }

    public TransactionId pay(Order order, Customer customer) {
        if (!order.canBePaid()) {
            throw new IllegalOrderTransitionException(order.status().name(), "pay");
        }
        Money total = pricingService.total(order, customer);
        TransactionId transactionId = gatewayFactory.forCurrency(total.currency()).charge(total);
        order.confirmPayment(transactionId);
        return transactionId;
    }
}
