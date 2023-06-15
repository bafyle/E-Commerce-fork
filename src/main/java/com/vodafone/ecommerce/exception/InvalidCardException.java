package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class InvalidCardException extends APIException{
    public InvalidCardException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
