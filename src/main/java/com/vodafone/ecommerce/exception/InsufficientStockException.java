package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends APIException{
    public InsufficientStockException(String message) {
        super(message);
    }

    //TODO: error code?
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}

