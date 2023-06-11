package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndCustomerId(Long orderId, Long customerId);

    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
