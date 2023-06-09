package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends UserBaseRepo<Customer> {

}
