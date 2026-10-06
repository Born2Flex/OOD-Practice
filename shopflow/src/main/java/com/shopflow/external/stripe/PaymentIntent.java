package com.shopflow.external.stripe;

public record PaymentIntent(String id, long amount, String currency, String status) {
}
