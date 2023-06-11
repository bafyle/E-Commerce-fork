package com.vodafone.ecommerce.service;

import java.io.IOException;
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
import org.springframework.stereotype.Service;

import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.repository.AdminRepo;
import com.vodafone.ecommerce.repository.CustomerRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.vodafone.ecommerce.util.StringUtils;

@Service
public class AuthenticationService
{
    private static final String EMAIL = "ecommerce-gp@vodafone.com";
    @Autowired
    CustomerRepo cr;

    @Autowired AdminRepo ar;
    
    @Autowired
    JavaMailSender mailSender;
    
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
    public void sendVerificationEmail(Customer user, HttpServletRequest request) throws 
        MessagingException,
        UnsupportedEncodingException, MalformedURLException, URISyntaxException 
    {
        String toAddress = user.getEmail();
        String fromAddress = EMAIL;
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

    public void sendActivationEmail(Customer user, HttpServletRequest request) throws 
        MessagingException,
        UnsupportedEncodingException, MalformedURLException, URISyntaxException 
    {
        String toAddress = user.getEmail();
        String fromAddress = EMAIL;
        String senderName = "E-commerce";
        String subject = "Please activate your e-commerce email";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to re-activate your email,"
                +" since it has been locked due to multiple login attempts:<br>"
                +"<br> if you think that someone is trying to access your account, please change your password"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ACTIVATE</a></h3>"
                + "Thank you,<br>"
                + "E-commerce";
            
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

    public void processLoginTry(String email, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Optional<Customer> customer = cr.findByEmail(email);
        var customerFromDB = customer.get();
        if(
            customerFromDB == null 
            || customerFromDB.isLocked()
            || !customerFromDB.isEnabled()
            )
        {
            // response.sendRedirect("/login-failed");
            return;
        }
        if(customerFromDB.getLoginTries() < 2)
        {
            customerFromDB.setLoginTries(customerFromDB.getLoginTries() + 1);
        }
        else
        {
            customerFromDB.setLocked(true);
            customerFromDB.setLoginTries(0);
            customerFromDB.setVerficationCode(StringUtils.createRandomString(15));
            
            try {
                sendActivationEmail(customerFromDB, request);
            } catch (UnsupportedEncodingException | MalformedURLException | MessagingException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        cr.save(customerFromDB);
    }
    
    
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
}

