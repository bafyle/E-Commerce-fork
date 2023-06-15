package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class InsufficientAmountInCardException extends MVCException {
    public InsufficientAmountInCardException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
