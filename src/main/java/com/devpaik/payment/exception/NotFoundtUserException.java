package com.devpaik.payment.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundtUserException extends ResponseStatusException {

    public NotFoundtUserException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
