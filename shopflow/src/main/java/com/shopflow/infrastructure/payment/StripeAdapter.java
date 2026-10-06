package com.shopflow.infrastructure.payment;

import com.shopflow.external.stripe.CardException;
import com.shopflow.external.stripe.PaymentIntent;
import com.shopflow.external.stripe.StripeClient;
import com.shopflow.model.money.Money;
import com.shopflow.model.payment.PaymentDeclinedException;
import com.shopflow.model.payment.PaymentGateway;
import com.shopflow.model.payment.TransactionId;

public class StripeAdapter implements PaymentGateway {

    private final StripeClient client;

    public StripeAdapter(StripeClient client) {
        this.client = client;
    }

    @Override
    public TransactionId charge(Money amount) {
        long minorUnits = amount.amount().movePointRight(amount.currency().getDefaultFractionDigits()).longValueExact();
        String currency = amount.currency().getCurrencyCode().toLowerCase();
        try {
            PaymentIntent intent = client.createPaymentIntent(minorUnits, currency);
            return new TransactionId(intent.id());
        } catch (CardException e) {
            throw new PaymentDeclinedException(e.getCode());
        }
    }
}
