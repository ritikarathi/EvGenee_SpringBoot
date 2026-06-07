package com.voltx.evgenee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends EvGeneeException {
    public BadRequestException(String message) {
        super(message, "BAD_REQUEST");
    }
}