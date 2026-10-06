package com.shopflow.model;

import java.math.BigDecimal;
import java.math.MathContext;

public record Percentage(BigDecimal value) {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    public Percentage {
        if (value.signum() < 0 || value.compareTo(HUNDRED) > 0) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100: " + value);
        }
    }

    public static Percentage of(int value) {
        return new Percentage(BigDecimal.valueOf(value));
    }

    BigDecimal asFraction() {
        return value.divide(HUNDRED, MathContext.DECIMAL64);
    }
}
