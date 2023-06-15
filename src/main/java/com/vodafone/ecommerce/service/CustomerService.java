package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.exception.UserAlreadyExists;
import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    private final CartService cartService;

    @Autowired
    public CustomerService(CustomerRepo customerRepo, CartService cartService) {
        this.customerRepo = customerRepo;
        this.cartService = cartService;
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer with this id not found.");
        }
        return customer.get();
    }

    public Customer addCustomer(Customer customer) {
        if (customerRepo.findByEmail(customer.getEmail()).isPresent()) {
            throw new DuplicateEntityException("Account with this email already exists.");
        }
        customer.setCart(new Cart());
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(Customer customer, Long id) {
        Optional<Customer> customerById = customerRepo.findById(id);

        if (customerById.isEmpty()) {
            throw new NotFoundException("Customer id not found.");
        }

        if (customerRepo.findByEmail(customer.getEmail()).isEmpty())
        {
            throw new UserAlreadyExists("Choose Different Email Address");
        }
        // TODO: limit email change?
        customer.setId(id);
        customer.setCart(customerById.get().getCart());
        return customerRepo.save(customer);
    }

    public void deleteCustomer(Long id){
        if (customerRepo.findById(id).isEmpty()) {
            throw new NotFoundException("Customer id not found.");
        }
        customerRepo.deleteById(id);
    }
}
