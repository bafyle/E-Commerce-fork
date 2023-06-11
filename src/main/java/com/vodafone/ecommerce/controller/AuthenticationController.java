package com.vodafone.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    
    // Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    // Register
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Reset Password
    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "resetPassword";
    }
    // Verification Confirmed
    @GetMapping("/verify")
    public String verifyEmail() {
        return "verify";
    }
}
