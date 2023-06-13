package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.User;
import com.vodafone.ecommerce.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// FirstTie Password Change
// HomePage - Admin
// ProductDetails - Admin
// MyAccount - Admin
// Assign New Admin
// View All Admins - Admin

//@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String getAllAdmins(Model model) {
        List<Admin> admins = adminService.getAllAdmins();
        System.out.println("??????????????/" + admins);
        model.addAttribute("all_admins",admins);
        return "admin-adminsRead";
    }

    @GetMapping("/{id}")
    public String getAdminById(@PathVariable(name = "id") Long id, Model model) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin", admin);
        return "admin-adminRead";
    }

    @GetMapping("/add")
    public String createNewAdmin(Model model) {
        Admin createdAdmin = new Admin();
        model.addAttribute("admin", createdAdmin);
        return "admin-adminCreate";
    }

    @PostMapping
    public String addAdmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("admin", admin);
            return "admin-adminCreate";
        }

        adminService.addAdmin(admin);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String updateAdmin(@PathVariable(name = "id") Long id, Model model) {
        Admin admin = adminService.getAdminById(id);
        Admin updatedAdmin = adminService.updateAdmin(admin, id);
        model.addAttribute("admin", updatedAdmin);
        return "admin-adminEdit";
    }

    @PostMapping("/{id}/edit")
    public String updateAdmin(@PathVariable("id") Long adminId,
                              @Valid @ModelAttribute("admin") Admin admin,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("admin", admin);
            return "admin-adminEdit";
        }
        adminService.updateAdmin(admin, adminId);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/delete")
    public String deleteAdmin(@PathVariable(name = "id") Long id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin";
    }
}
