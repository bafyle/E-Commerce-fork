package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public abstract class MVCException extends RuntimeException{
    public MVCException(String message) {
        super(message);
    }
    public abstract HttpStatus getStatus();

//    public abstract String getErrorTitle();

    public String getView() {
        return "error";
    }
}
