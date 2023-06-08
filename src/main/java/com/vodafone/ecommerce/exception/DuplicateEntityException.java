package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends APIException{
    public DuplicateEntityException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
