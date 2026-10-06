package com.shopflow.external.stripe;

import java.util.UUID;

public class StripeClient {

    private final boolean declineCharges;

    public StripeClient(boolean declineCharges) {
        this.declineCharges = declineCharges;
    }

    public PaymentIntent createPaymentIntent(long amountInCents, String currency) throws CardException {
        if (declineCharges) {
            throw new CardException("card_declined", "Your card was declined.");
        }
        return new PaymentIntent("pi_" + UUID.randomUUID(), amountInCents, currency, "succeeded");
    }
}
