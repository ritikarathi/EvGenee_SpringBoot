package com.voltx.evgenee.exceptions;

import lombok.Getter;

@Getter
public class EvGeneeException extends RuntimeException {
    private final String errorCode;

    public EvGeneeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}