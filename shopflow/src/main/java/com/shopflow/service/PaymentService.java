package com.shopflow.service;

import com.shopflow.event.OrderEventPublisher;
import com.shopflow.event.OrderStatusChanged;
import com.shopflow.exception.IllegalOrderTransitionException;
import com.shopflow.integration.PaymentGatewayFactory;
import com.shopflow.model.customer.Customer;
import com.shopflow.model.money.Money;
import com.shopflow.model.order.Order;
import com.shopflow.model.order.OrderStatus;
import com.shopflow.model.payment.TransactionId;

public class PaymentService {

    private final PricingService pricingService;
    private final PaymentGatewayFactory gatewayFactory;
    private final OrderEventPublisher eventPublisher;

    public PaymentService(PricingService pricingService, PaymentGatewayFactory gatewayFactory, OrderEventPublisher eventPublisher) {
        this.pricingService = pricingService;
        this.gatewayFactory = gatewayFactory;
        this.eventPublisher = eventPublisher;
    }

    public TransactionId pay(Order order, Customer customer) {
        if (!order.canBePaid()) {
            throw new IllegalOrderTransitionException(order.status().name(), "pay");
        }
        OrderStatus from = order.status();
        Money total = pricingService.total(order, customer);
        TransactionId transactionId = gatewayFactory.forCurrency(total.currency()).charge(total);
        order.confirmPayment(transactionId);
        eventPublisher.publish(new OrderStatusChanged(order.id(), order.customerId(), from, order.status()));
        return transactionId;
    }
}
