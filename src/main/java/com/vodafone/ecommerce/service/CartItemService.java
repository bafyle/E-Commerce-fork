package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.repository.CartItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemRepo cartItemRepo;

    @Autowired
    public CartItemService(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }

    @Transactional
    public void deleteAllCartItems(Long cartId) {
        cartItemRepo.deleteAllByCartId(cartId);
    }

    public void deleteCartItemById(Long cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

    public CartItem updateCartItem(CartItem cartItem, Long cartItemId) {
        cartItem.setId(cartItemId);
        return cartItemRepo.save(cartItem);
    }
}
