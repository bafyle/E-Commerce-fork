package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.DuplicateEntityException;
import com.vodafone.ecommerce.exception.InsufficientStockException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepo cartRepo;
    private final ProductService productService;

    private final CartItemService cartItemService;

    @Autowired
    public CartService(CartRepo cartRepo, ProductService productService, CartItemService cartItemService) {
        this.cartRepo = cartRepo;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    public Cart getCartByCustomerId(Long customerId) {
        Optional<Cart> cart = cartRepo.findByCustomerId(customerId);
        if (cart.isEmpty()) {
            throw new NotFoundException("Cart with this id not found.");
        }
        return cart.get();
    }

    public Cart addCartItem(Long customerId, CartItem cartItem) {
        // check if cart exists
        Cart cart = getCartByCustomerId(customerId);

        // check if product exists
        Product productById = productService.getProductById(cartItem.getProduct().getId());

        if (productById.getStock() < cartItem.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock requested.");
        }

        if (cart.getCartItems().stream().anyMatch(cartItem1 -> cartItem1.getProduct().getId().equals(cartItem.getProduct().getId()))) {
            throw new DuplicateEntityException("This product has already been added to cart");
        }

        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);
        return cartRepo.save(cart);
    }

    public Cart updateCartItem(CartItem cartItem, Long customerId, Long cartItemId) {
        // check if cart exists
        Cart cart = getCartByCustomerId(customerId);

        // check if product exists
        Product productById = productService.getProductById(cartItem.getProduct().getId());

        if (productById.getStock() < cartItem.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock requested.");
        }

        if (cart.getCartItems().stream().noneMatch(cartItem1 -> cartItem1.getId().equals(cartItemId))) {
            throw new DuplicateEntityException("This product does not exist in the cart");
        }

        cartItem.setCart(cart);

        cartItemService.updateCartItem(cartItem, cartItemId);
        return cart;
    }

    public Cart deleteAllCartItems(Long customerId) {
        Cart cart = getCartByCustomerId(customerId);

        cart.getCartItems().clear();

        cartItemService.deleteAllCartItems(cart.getId());
        return cart;
    }

    public Cart deleteCartItem(Long customerId, Long cartItemId) {
        Cart cart = getCartByCustomerId(customerId);

        cartItemService.deleteCartItemById(cartItemId);
    return cart;
    }
}
