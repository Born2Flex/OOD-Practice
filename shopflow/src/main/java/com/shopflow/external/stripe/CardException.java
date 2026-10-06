package com.shopflow.external.stripe;

public class CardException extends Exception {

    private final String code;

    public CardException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
