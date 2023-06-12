package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.CustomerService;
import com.vodafone.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasAuthority('Customer')")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Customer')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(name = "id") Long id,
                                                    @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(id, user.getUser().getId());
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer,
                                                @AuthenticationPrincipal SecurityUser user) {
        Customer customerRes = customerService.addCustomer(customer);
        return new ResponseEntity<>(customerRes, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable(name = "id") Long id,
                                                   @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(id, user.getUser().getId());
        Customer customerRes = customerService.updateCustomer(customer, id);
        return new ResponseEntity<>(customerRes, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") Long id,
                                                   @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(id, user.getUser().getId());
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
