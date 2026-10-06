package com.shopflow.integration;

import com.shopflow.exception.PaymentDeclinedException;
import com.shopflow.external.paypal.CaptureResponse;
import com.shopflow.external.paypal.PayPalClient;
import com.shopflow.external.paypal.PayPalOrder;
import com.shopflow.model.Money;
import com.shopflow.model.TransactionId;

public class PayPalAdapter implements PaymentGateway {

    private final PayPalClient client;

    public PayPalAdapter(PayPalClient client) {
        this.client = client;
    }

    @Override
    public TransactionId charge(Money amount) {
        PayPalOrder order = client.createOrder(amount.amount().toPlainString(), amount.currency().getCurrencyCode());
        CaptureResponse capture = client.capture(order.id());
        if (!"COMPLETED".equals(capture.status())) {
            throw new PaymentDeclinedException(capture.issue());
        }
        return new TransactionId(capture.captureId());
    }
}
