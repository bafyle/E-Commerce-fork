package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class InsufficientAmountInCardException extends APIException{
    public InsufficientAmountInCardException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
