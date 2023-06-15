package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends MVCException {
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
