package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByIdAndOrderIdAndCustomerId(Long orderItemId, Long orderId, Long customerId);
}
