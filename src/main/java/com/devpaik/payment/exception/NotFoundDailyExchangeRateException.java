package com.devpaik.payment.exception;

public class NotFoundDailyExchangeRateException extends RuntimeException {

    public NotFoundDailyExchangeRateException(String message) {
        super(message);
    }
}
