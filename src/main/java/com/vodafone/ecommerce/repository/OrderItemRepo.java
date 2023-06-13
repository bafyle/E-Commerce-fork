package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByIdAndOrderIdAndOrderCustomerId(Long orderItemId, Long orderId, Long customerId);

    @Query("SELECT AVG(o.rating) FROM OrderItem o WHERE o.product.id = ?1")
    Double avgRatingByProductId(Long id);
}
