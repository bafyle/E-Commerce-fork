package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.InvalidInputException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Admin;
import com.vodafone.ecommerce.repository.AdminRepo;
import com.vodafone.ecommerce.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepo adminRepo;

    @Autowired
    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public Admin getAdminById(Long id) {
        Optional<Admin> admin = adminRepo.findById(id);

        if (admin.isEmpty()) {
            throw new NotFoundException("Admin not found");
        }

        return admin.get();
    }


    public Admin addAdmin(Admin admin) {
        if (!ValidationUtil.validateEmail(admin.getEmail())) {
            throw new InvalidInputException("Invalid email");
        }
        if (admin.getName().trim().length()<1) {
            throw new InvalidInputException("Name cannot be empty");
        }
        if (adminRepo.findByEmail(admin.getEmail()).isPresent()) {
            throw new DuplicateEntityException("Account with this email already exists");
        }

        return adminRepo.save(admin);
    }

    public Admin updateAdmin(Admin admin, Long id) {
        Optional<Admin> byId = adminRepo.findById(id);

        if (admin.getName().trim().length()<1) {
            throw new InvalidInputException("Name cannot be empty");
        }
        if (byId.isEmpty()) {
            throw new NotFoundException("Admin not found");
        }

        admin.setId(id);
        admin.setEmail(byId.get().getEmail());
        return adminRepo.save(admin);
    }

    public void deleteAdmin(Long id) {
        if (adminRepo.findById(id).isEmpty()) {
            throw new NotFoundException("Admin not found");
        }

        adminRepo.deleteById(id);
    }
}
