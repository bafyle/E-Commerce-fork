package com.vodafone.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.service.AuthenticationService;
import com.vodafone.ecommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("")
public class UserController
{

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authService;

    @GetMapping("/")
    public String home(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getPrincipal());
        return "home";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @PostMapping("/login-failed")
    public String login_failed()
    {
        return "login_failed";
    }

    @GetMapping("/logout")
    public String logout()
    {
        return "logout";
    }

    @GetMapping("/logout-success")
    public String loggedOut()
    {
        return "logout_success";
    }
    @GetMapping("/register")
    public String registerPage(Model model)
    {
        Customer user = new Customer();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") Customer customer, HttpServletRequest request)
    {
        userService.registerCustomer(customer, request);
        return "register_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (authService.verifyCustomer(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}
