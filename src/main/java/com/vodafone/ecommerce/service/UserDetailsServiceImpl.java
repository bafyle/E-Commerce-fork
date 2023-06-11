package com.vodafone.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.repository.AdminRepo;
import com.vodafone.ecommerce.repository.CustomerRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    CustomerRepo cr;
    
    @Autowired
    AdminRepo ar;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<Customer> customerFromDB = cr.findByEmail(username);
        if (customerFromDB.isPresent())
            return new SecurityUser(customerFromDB.get());
        Optional<Admin> adminFromDB = ar.findByEmail(username);
        if(adminFromDB.isEmpty())
            throw new UsernameNotFoundException("No user found");
        return new SecurityUser(adminFromDB.get());
    }
}
