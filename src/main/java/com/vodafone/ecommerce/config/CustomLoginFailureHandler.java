package com.vodafone.ecommerce.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.vodafone.ecommerce.exception.MailServerException;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.repository.CustomerRepo;
import com.vodafone.ecommerce.service.AuthenticationService;
import com.vodafone.ecommerce.service.LoginService;
import com.vodafone.ecommerce.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler
{
    @Autowired
    private LoginService loginService;
     
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException
    {
        String email = request.getParameter("username");
        try
        {
            loginService.processLoginTry(email, request, response);
        }
        catch(MailException e)
        {
            response.sendRedirect("/error");
            return;
        }
        String redirectUrl = request.getContextPath() + "/login-failed";
        response.sendRedirect(redirectUrl);
    }
}
