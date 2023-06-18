package com.vodafone.ecommerce.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.vodafone.ecommerce.exception.MailServerException;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.repository.CustomerRepo;
import com.vodafone.ecommerce.util.StringUtils;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginService 
{
    @Autowired
    private CustomerRepo cr;

    @Autowired 
    private MailService mailService;
    
    public void processLoginTry(String email, HttpServletRequest request, HttpServletResponse response) throws IOException, MailException
    {
        Optional<Customer> customer = cr.findByEmail(email);
        if(
            customer.isEmpty() == true
            || customer.get().isLocked()
            || !customer.get().isEnabled()
            )
        {
            return;
        }
        var customerFromDB = customer.get();
        if(customerFromDB.getLoginTries() < 2)
        {
            customerFromDB.setLoginTries(customerFromDB.getLoginTries() + 1);
        }
        else
        {
            customerFromDB.setLocked(true);
            customerFromDB.setLoginTries(0);
            customerFromDB.setVerficationCode(StringUtils.createRandomString(15));
            cr.save(customerFromDB);
            try {
               mailService.sendActivationEmail(customerFromDB, request);
            } catch (UnsupportedEncodingException | MalformedURLException | MessagingException | URISyntaxException e) {
                throw new MailServerException("Mail service not available right now, please contact support team");
            }
        }
    }
}
