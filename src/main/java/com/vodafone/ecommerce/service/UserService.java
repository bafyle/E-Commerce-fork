package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.UserAlreadyExists;
import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.User;
import com.vodafone.ecommerce.repository.AdminRepo;
import com.vodafone.ecommerce.repository.CartRepo;
import com.vodafone.ecommerce.repository.CustomerRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    @Autowired
    JavaMailSender mailSender;

    // Customer registeration
    public Customer registerCustomer(Customer customer, HttpServletRequest request)
    {
        checkIfUserExists(customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Random rand = new Random();
        String randomCode = rand.ints(48, 123)
                .filter(num -> (num<58 || num>64) && (num<91 || num>96))
                .limit(15)
                .mapToObj(c -> (char)c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                .toString();
        customer.setVerficationCode(randomCode);
        customer.setEnabled(false);
        var cartFromDB = cartRepo.save(new Cart());
        customer.setCart(cartFromDB);
        Customer customerToReturn = cr.save(customer);
        try {
            sendVerificationEmail(customerToReturn, request);
        } catch (UnsupportedEncodingException | MalformedURLException | MessagingException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return customerToReturn;
    }

    public boolean verify(String verificationCode) {
        Optional<Customer> user = cr.findByverficationCode(verificationCode);
         
        if (user.isEmpty()) {
            return false;
        }
        var u = user.get();
        u.setVerficationCode(null);
        u.setEnabled(true);
        cr.save(u);
        return true;
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

    private static String getCurrentUrl(HttpServletRequest request) throws URISyntaxException, MalformedURLException{
        URL url = new URL(request.getRequestURL().toString());
        String host  = url.getHost();
        String userInfo = url.getUserInfo();
        String scheme = url.getProtocol();
        int port = url.getPort();
        String path = (String) request.getAttribute("javax.servlet.forward.request_uri");
        String query = (String) request.getAttribute("javax.servlet.forward.query_string");
    
        URI uri = new URI(scheme,userInfo,host,port,path,query,null);
        return uri.toString();
    }
    private void sendVerificationEmail(Customer user, HttpServletRequest request) throws 
        MessagingException,
        UnsupportedEncodingException, MalformedURLException, URISyntaxException 
    {
        
            String toAddress = user.getEmail();
            String fromAddress = "ecommerce-gp@vodafone.com";
            String senderName = "E-commerce";
            String subject = "Please verify your registration";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "Your company name.";
             
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
             
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
             
            content = content.replace("[[name]]", user.getEmail());
            String verifyURL = getCurrentUrl(request) + "/verify?code=" + user.getVerficationCode();
             
            content = content.replace("[[URL]]", verifyURL);
             
            helper.setText(content, true);
             
            mailSender.send(message);
    }

}
