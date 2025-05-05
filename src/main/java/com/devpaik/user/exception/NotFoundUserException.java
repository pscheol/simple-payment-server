package com.devpaik.user.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundUserException extends ResponseStatusException {

    public NotFoundUserException(HttpStatusCode status, String reason) {
        super(status, reason);
    }


}
