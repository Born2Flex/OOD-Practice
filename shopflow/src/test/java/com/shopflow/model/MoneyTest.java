package com.shopflow.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void roundsToCurrencyMinorUnits() {
        assertThat(Money.of("10", "EUR").amount()).hasToString("10.00");
        assertThat(Money.of("100", "JPY").amount()).hasToString("100");
    }

    @Test
    void equalAmountsWithDifferentScaleAreEqual() {
        assertThat(Money.of("10", "EUR")).isEqualTo(Money.of("10.000", "EUR"));
    }

    @Test
    void usesBankersRounding() {
        assertThat(Money.of("0.125", "EUR")).isEqualTo(Money.of("0.12", "EUR"));
        assertThat(Money.of("0.135", "EUR")).isEqualTo(Money.of("0.14", "EUR"));
    }

    @Test
    void calculatesPercentage() {
        assertThat(Money.of("19.99", "EUR").percentage(Percentage.of(10))).isEqualTo(Money.of("2.00", "EUR"));
    }

    @Test
    void rejectsArithmeticAcrossCurrencies() {
        assertThatThrownBy(() -> Money.of("1", "EUR").add(Money.of("1", "USD")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Currency mismatch");
    }

    @Test
    void percentageMustBeBetweenZeroAndHundred() {
        assertThatThrownBy(() -> Percentage.of(-1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Percentage.of(101)).isInstanceOf(IllegalArgumentException.class);
    }
}
