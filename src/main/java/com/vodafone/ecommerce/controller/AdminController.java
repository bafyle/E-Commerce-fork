package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// FirstTie Password Change
// HomePage - Admin
// ProductDetails - Admin
// MyAccount - Admin
// Assign New Admin
// View All Admins - Admin

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
}
