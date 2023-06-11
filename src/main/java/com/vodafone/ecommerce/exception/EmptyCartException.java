package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class EmptyCartException extends APIException{
    public EmptyCartException(String message) {
        super(message);
    }

    //TODO: error code?
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
