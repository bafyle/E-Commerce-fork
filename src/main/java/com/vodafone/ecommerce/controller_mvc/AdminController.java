package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.model.User;
import com.vodafone.ecommerce.service.AdminService;
import com.vodafone.ecommerce.service.UserService;
import com.vodafone.ecommerce.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('Admin')")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService) {

        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllAdmins(Model model) {
        List<Admin> admins = adminService.getAllAdmins();
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
    public String addAdmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("admin", admin);
            return "admin-adminCreate";
        }

        userService.createAdmin(admin.getEmail(), request);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String updateAdmin(@PathVariable(name = "id") Long id, Model model) {
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin", admin);
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

        Admin existingAdmin = adminService.getAdminById(adminId);
        existingAdmin.setName(admin.getName());

        userService.updateAdmin(adminId, existingAdmin);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/delete")
    public String deleteAdmin(@PathVariable(name = "id") Long id,
                              @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isLoggedInUserThrowException(id, user.getUser().getId());
        userService.deleteAdmin(id);
        return "redirect:/admin";
    }
}