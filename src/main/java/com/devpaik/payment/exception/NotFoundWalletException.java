package com.devpaik.payment.exception;

public class NotFoundWalletException extends RuntimeException {

    public NotFoundWalletException(String message) {
        super(message);
    }
}
