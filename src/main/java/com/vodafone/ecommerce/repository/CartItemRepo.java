package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long cartId);

    Optional<CartItem> getCartItemByIdAndCartCustomerId(Long cartItemId, Long customerId);

    void deleteAllByProductId(Long id);
}
