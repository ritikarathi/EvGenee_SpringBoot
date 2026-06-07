package com.voltx.evgenee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends EvGeneeException {
    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED");
    }
}