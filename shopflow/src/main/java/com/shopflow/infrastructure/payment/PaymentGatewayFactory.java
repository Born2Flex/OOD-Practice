package com.shopflow.infrastructure.payment;

import com.shopflow.external.paypal.PayPalClient;
import com.shopflow.external.stripe.StripeClient;
import com.shopflow.model.payment.PaymentGateway;

import java.util.Currency;

public class PaymentGatewayFactory {

    private final StripeClient stripeClient;
    private final PayPalClient payPalClient;

    public PaymentGatewayFactory(StripeClient stripeClient, PayPalClient payPalClient) {
        this.stripeClient = stripeClient;
        this.payPalClient = payPalClient;
    }

    public PaymentGateway forCurrency(Currency currency) {
        return switch (currency.getCurrencyCode()) {
            case "USD" -> new StripeAdapter(stripeClient);
            case "EUR" -> new PayPalAdapter(payPalClient);
            default -> throw new IllegalArgumentException("No payment provider for currency " + currency);
        };
    }
}
