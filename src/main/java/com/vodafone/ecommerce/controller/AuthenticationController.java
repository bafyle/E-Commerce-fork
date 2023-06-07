package com.vodafone.ecommerce.controller;

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
