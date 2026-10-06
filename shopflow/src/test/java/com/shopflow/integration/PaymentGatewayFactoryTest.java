package com.shopflow.integration;

import com.shopflow.exception.PaymentDeclinedException;
import com.shopflow.external.paypal.PayPalClient;
import com.shopflow.external.stripe.StripeClient;
import com.shopflow.model.Money;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentGatewayFactoryTest {

    private final PaymentGatewayFactory approving = new PaymentGatewayFactory(new StripeClient(false), new PayPalClient(false));
    private final PaymentGatewayFactory declining = new PaymentGatewayFactory(new StripeClient(true), new PayPalClient(true));

    @Test
    void routesUsdToStripe() {
        assertThat(approving.forCurrency(Currency.getInstance("USD"))).isInstanceOf(StripeAdapter.class);
    }

    @Test
    void routesEurToPayPal() {
        assertThat(approving.forCurrency(Currency.getInstance("EUR"))).isInstanceOf(PayPalAdapter.class);
    }

    @Test
    void rejectsUnsupportedCurrency() {
        assertThatThrownBy(() -> approving.forCurrency(Currency.getInstance("GBP")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("GBP");
    }

    @Test
    void stripeChargeReturnsPaymentIntentId() {
        assertThat(approving.forCurrency(Currency.getInstance("USD")).charge(Money.of("19.99", "USD")).value())
                .startsWith("pi_");
    }

    @Test
    void payPalChargeReturnsCaptureId() {
        assertThat(approving.forCurrency(Currency.getInstance("EUR")).charge(Money.of("19.99", "EUR")).value())
                .isNotBlank();
    }

    @Test
    void stripeDeclineBecomesPaymentDeclinedException() {
        assertThatThrownBy(() -> declining.forCurrency(Currency.getInstance("USD")).charge(Money.of("19.99", "USD")))
                .isInstanceOf(PaymentDeclinedException.class)
                .hasMessage("Payment declined: card_declined");
    }

    @Test
    void payPalDeclineBecomesPaymentDeclinedException() {
        assertThatThrownBy(() -> declining.forCurrency(Currency.getInstance("EUR")).charge(Money.of("19.99", "EUR")))
                .isInstanceOf(PaymentDeclinedException.class)
                .hasMessage("Payment declined: INSTRUMENT_DECLINED");
    }
}
