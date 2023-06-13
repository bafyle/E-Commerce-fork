package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.UserAlreadyExists;
import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.User;
import com.vodafone.ecommerce.repository.AdminRepo;
import com.vodafone.ecommerce.repository.CartRepo;
import com.vodafone.ecommerce.repository.CustomerRepo;
import com.vodafone.ecommerce.util.StringUtils;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired AuthenticationService authService;

    // Customer registeration
    public Customer registerCustomer(Customer customer, HttpServletRequest request)
    {
        checkIfUserExists(customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        String randomCode = StringUtils.createRandomString(15);
        customer.setVerficationCode(randomCode);
        customer.setEnabled(false);
        var cartFromDB = cartRepo.save(new Cart());
        customer.setCart(cartFromDB);
        Customer customerToReturn = cr.save(customer);
        try {
            authService.sendVerificationEmail(customerToReturn, request);
        } catch (UnsupportedEncodingException | MalformedURLException | MessagingException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return customerToReturn;
    }

    public Admin createAdmin(String adminEmail, HttpServletRequest request)
    {
        var admin = new Admin();
        admin.setEmail(adminEmail);
        checkIfUserExists(admin);
        admin.setEnabled(false);
        admin.setLocked(true);
        admin.setVerficationCode(StringUtils.createRandomString(15));
        admin.setPassword(StringUtils.createRandomString(10));
        try {
            authService.sendAdminVerficationEmail(admin, request);
        } catch (UnsupportedEncodingException | MalformedURLException | MessagingException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Admin adminToReturn = ar.save(admin);
        return adminToReturn;
    }

    public void deleteAdmin(Long adminId)
    {
        ar.deleteById(adminId);;
    }

    public void deleteAdmin(String email)
    {
        ar.deleteByEmail(email);
    }

    public Admin updateAdmin(Long adminId, Admin adminNewData)
    {
        var optionalAdmin = ar.findById(adminId);
        if(optionalAdmin.isEmpty())
            throw new UsernameNotFoundException("No user with this id");
        Admin adminFromDB = optionalAdmin.get();
        if(!adminFromDB.getEmail().equals(adminNewData.getEmail()))
        {
            checkIfUserExists(adminNewData);
        }
        Admin newAdmin = new Admin();
        // set newAdmin data from adminNewData and save newAdminData
        return ar.save(newAdmin);
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
