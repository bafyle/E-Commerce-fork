package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findByIdAndCustomerId(Long addressId, Long customerId);
}
