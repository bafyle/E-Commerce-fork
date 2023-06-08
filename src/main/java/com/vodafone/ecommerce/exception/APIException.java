package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public abstract class APIException extends RuntimeException{
    public APIException(String message) {
        super(message);
    }
    public abstract HttpStatus getStatus();
}
