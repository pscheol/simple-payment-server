package com.devpaik.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundDailyExchangeRateException extends ResponseStatusException {
    public NotFoundDailyExchangeRateException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
