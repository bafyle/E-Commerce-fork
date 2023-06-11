package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long cartId);
}
