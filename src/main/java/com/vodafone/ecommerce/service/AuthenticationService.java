package com.vodafone.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.repository.AdminRepo;
import com.vodafone.ecommerce.repository.CustomerRepo;


@Service
public class AuthenticationService
{
    
    @Autowired
    CustomerRepo cr;

    @Autowired AdminRepo ar;
    
    @Autowired
    JavaMailSender mailSender;

    @Autowired PasswordEncoder passwordEncoder;

    public boolean verifyCustomer(String verificationCode) {
        Optional<Customer> optionalUser = cr.findByverficationCode(verificationCode);
        if (optionalUser.isEmpty()) 
            return false;
        var user = optionalUser.get();
        user.setVerficationCode(null);
        user.setEnabled(true);
        user.setLocked(false);
        cr.save(user);
        return true;
    }

    public void resetAdminPassword(String code, String newRawPassword)
    {
        Optional<Admin> optionalAdmin = ar.findByverficationCode(code);
        if(optionalAdmin.isEmpty())
            throw new UsernameNotFoundException("no user with this id");
        Admin admin = optionalAdmin.get();
        admin.setPassword(passwordEncoder.encode(newRawPassword));
        admin.setEnabled(true);
        admin.setLocked(false);
        admin.setVerficationCode(null);
        ar.save(admin);
    }

}

