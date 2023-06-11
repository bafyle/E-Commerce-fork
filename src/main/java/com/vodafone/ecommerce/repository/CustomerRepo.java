package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends UserBaseRepo<Customer> {
    @Query("SELECT u FROM User u WHERE u.verficationCode = ?1")
    Optional<Customer> findByverficationCode(String code);
}
