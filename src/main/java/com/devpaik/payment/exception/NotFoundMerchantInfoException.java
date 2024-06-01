package com.devpaik.payment.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundMerchantInfoException extends ResponseStatusException {
    public NotFoundMerchantInfoException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
