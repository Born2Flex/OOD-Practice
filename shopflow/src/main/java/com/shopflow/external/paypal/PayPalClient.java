package com.shopflow.external.paypal;

import java.util.UUID;

public class PayPalClient {

    private final boolean declineCaptures;

    public PayPalClient(boolean declineCaptures) {
        this.declineCaptures = declineCaptures;
    }

    public PayPalOrder createOrder(String amount, String currencyCode) {
        return new PayPalOrder(UUID.randomUUID().toString(), "CREATED");
    }

    public CaptureResponse capture(String orderId) {
        return declineCaptures
                ? new CaptureResponse(null, "DECLINED", "INSTRUMENT_DECLINED")
                : new CaptureResponse(UUID.randomUUID().toString(), "COMPLETED", null);
    }
}
