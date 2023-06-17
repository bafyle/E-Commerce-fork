package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends MVCException {
    public AddressNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
