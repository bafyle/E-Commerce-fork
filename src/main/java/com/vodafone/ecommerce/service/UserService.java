package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.UserAlreadyExists;
import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.User;
import com.vodafone.ecommerce.repository.AdminRepo;
import com.vodafone.ecommerce.repository.CartRepo;
import com.vodafone.ecommerce.repository.CustomerRepo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService
{
    @Autowired
    CustomerRepo cr;
    
    @Autowired
    AdminRepo ar;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Customer createCustomer(Customer customer)
    {
        checkIfUserExists(customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        var cartFromDB = cartRepo.save(new Cart());
        customer.setCart(cartFromDB);
        Customer customerToReturn = cr.save(customer);
        return customerToReturn;
    }

    public Admin createAdmin(Admin admin)
    {
        checkIfUserExists(admin);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin adminToReturn = ar.save(admin);
        return adminToReturn;
    }

    private void checkIfUserExists(User u)
    {
        boolean instanceofCustomer = u instanceof Customer;
        if(instanceofCustomer == true)
        {
            if(cr.findByEmail(u.getEmail()).isPresent())
                throw new UserAlreadyExists("User with this email already exists");
        }
        if(ar.findByEmail(u.getEmail()).isPresent())
            throw new UserAlreadyExists("User with this email already exists");
    }

}
