package com.vodafone.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.model.dto.PasswordReset;
import com.vodafone.ecommerce.service.AuthenticationService;
import com.vodafone.ecommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("")
public class UserController
{

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal SecurityUser user)
    {
        return "redirect:/product";
    }

    // @PreAuthorize("hasAuthority('Customer')")
    // @GetMapping("/customer")
    // public String customerHome(Model model, @AuthenticationPrincipal SecurityUser user)
    // {
    //     // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     model.addAttribute("name", user.getUsername() + " customer home");
    //     return "home";
    // }

    // @PreAuthorize("hasAuthority('Admin')")
    // @GetMapping("/admin")
    // public String adminHome(Model model, @AuthenticationPrincipal SecurityUser user)
    // {
    //     // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     model.addAttribute("name", user.getUsername() + " admin home");
    //     return "home";
    // }

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
    public String verifyCustomer(@Param("code") String code) {
        if (authService.verifyCustomer(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/login-failed")
    public String loginFailed()
    {
        return "login_failed";
    }

    @GetMapping("/error")
    public String error(Model model, HttpServletResponse response)
    {
        model.addAttribute("code", response.getStatus());
        return "error";
    }

    @GetMapping("/password-reset")
    public String resetAdminPasswordPage(@Param("code") String code, Model model)
    {
        PasswordReset pr = new PasswordReset();
        pr.setAdminCode(code);
        model.addAttribute("passwordRecord", pr);
        return "password_reset_form";
    }
    @PostMapping("/password-reset")
    public String resetAdminPasswordPost(@ModelAttribute PasswordReset password)
    {
        authService.resetAdminPassword(password.getAdminCode(), password.getPassword());
        return "password_reset_success";
    }
}
