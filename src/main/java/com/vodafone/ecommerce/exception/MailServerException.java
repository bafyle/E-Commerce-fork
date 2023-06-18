package com.vodafone.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class MailServerException extends MVCException
{

    public MailServerException(String message)
    {
        super(message);
    }
    @Override
    public HttpStatus getStatus() {
        // TODO Auto-generated method stub
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    
}
