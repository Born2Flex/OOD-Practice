package com.shopflow.service;

import com.shopflow.exception.IllegalOrderTransitionException;
import com.shopflow.exception.PaymentDeclinedException;
import com.shopflow.external.paypal.PayPalClient;
import com.shopflow.external.stripe.StripeClient;
import com.shopflow.integration.PaymentGatewayFactory;
import com.shopflow.model.customer.Customer;
import com.shopflow.model.customer.CustomerType;
import com.shopflow.model.discount.StackedDiscount;
import com.shopflow.model.money.Money;
import com.shopflow.model.order.Order;
import com.shopflow.model.order.OrderLine;
import com.shopflow.model.order.OrderStatus;
import com.shopflow.model.payment.TransactionId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentServiceTest {

    private final Customer customer = new Customer(UUID.randomUUID(), CustomerType.REGULAR);
    private final PricingService pricing = new PricingService(StackedDiscount.of());

    @Test
    void approvedPaymentMarksOrderPaid() {
        PaymentService payments = new PaymentService(pricing, factory(false));
        Order order = order("USD");

        TransactionId transactionId = payments.pay(order, customer);

        assertThat(order.status()).isEqualTo(OrderStatus.PAID);
        assertThat(order.transactionId()).contains(transactionId);
    }

    @Test
    void declinedPaymentLeavesOrderNew() {
        PaymentService payments = new PaymentService(pricing, factory(true));
        Order order = order("EUR");

        assertThatThrownBy(() -> payments.pay(order, customer)).isInstanceOf(PaymentDeclinedException.class);
        assertThat(order.status()).isEqualTo(OrderStatus.NEW);
    }

    @Test
    void doesNotChargeOrderThatCannotBePaid() {
        PaymentService payments = new PaymentService(pricing, factory(true));
        Order order = order("USD");
        order.cancel();

        assertThatThrownBy(() -> payments.pay(order, customer))
                .isInstanceOf(IllegalOrderTransitionException.class);
    }

    private Order order(String currency) {
        return new Order(UUID.randomUUID(), customer.id(), List.of(new OrderLine(Money.of("50.00", currency), 2)));
    }

    private static PaymentGatewayFactory factory(boolean decline) {
        return new PaymentGatewayFactory(new StripeClient(decline), new PayPalClient(decline));
    }
}
