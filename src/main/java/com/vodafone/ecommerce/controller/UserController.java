package com.vodafone.ecommerce.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("")
public class UserController
{

    @GetMapping("/")
    public String home()
    {
        return "home";
    }

//    @GetMapping("/login")
//    public String login()
//    {
//        return "login";
//    }

    @GetMapping("/logout")
    public String logout()
    {
        return "logout";
    }

//    @GetMapping("/am-i-logged-in")
//    public String amILoggedIn( Model model)
//    {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        model.addAttribute("name", auth.getPrincipal());
//        return "currentUser";
//    }

    @GetMapping("/logout-success")
    public String loggedOut()
    {
        return "logout_success";
    }

}