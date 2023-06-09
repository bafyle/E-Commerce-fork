package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;


public class UserAlreadyExists extends APIException
{

    public UserAlreadyExists(String message)
    {
        super(message);
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

}
