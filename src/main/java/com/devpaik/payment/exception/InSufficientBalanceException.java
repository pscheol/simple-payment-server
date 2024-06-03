package com.devpaik.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InSufficientBalanceException extends ResponseStatusException {
    public InSufficientBalanceException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
