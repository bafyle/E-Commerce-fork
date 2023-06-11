package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends UserBaseRepo<Customer> {

    Optional<Customer> findByEmail(String email);
}
