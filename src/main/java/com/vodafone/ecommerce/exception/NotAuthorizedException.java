package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends MVCException {
    public NotAuthorizedException() {
        super("Not authorized to access this resource.");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
