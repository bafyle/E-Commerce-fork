package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}
