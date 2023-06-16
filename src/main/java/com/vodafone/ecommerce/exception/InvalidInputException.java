package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends MVCException{
    public InvalidInputException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
