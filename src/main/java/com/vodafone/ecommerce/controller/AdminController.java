package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.AdminService;
import com.vodafone.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// FirstTie Password Change
// HomePage - Admin
// ProductDetails - Admin
// MyAccount - Admin
// Assign New Admin
// View All Admins - Admin

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('Admin')")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return new ResponseEntity<>(adminService.getAllAdmins(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(adminService.getAdminById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.addAdmin(admin), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable(name = "id") Long id,
                                             @RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.updateAdmin(admin, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable(name = "id") Long id,
                                             @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isLoggedInUserThrowException(id, user.getUser().getId());
        adminService.deleteAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
