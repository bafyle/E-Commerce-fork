package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.repository.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private CartItemRepo cartItemRepo;

    @Autowired
    public CartItemService(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }
}
