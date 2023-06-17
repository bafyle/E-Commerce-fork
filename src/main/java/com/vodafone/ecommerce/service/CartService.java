package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.*;
import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.dto.CartItemDTO;
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

    public Cart addCartItem(Long customerId, CartItemDTO cartItemDTO) {
        // check if cart exists
        Cart cart = getCartByCustomerId(customerId);

        // check if product exists
        Product productById = productService.getProductById(cartItemDTO.getProductId());

        if (cartItemDTO.getQuantity() < 1) {
            throw new InvalidInputException("Quantity can't be less than 1");
        }

        if (productById.getIsArchived()) {
            throw new InsufficientStockException("Item not available");
        }

        if (productById.getStock() < cartItemDTO.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock requested.");
        }

        if (cart.getCartItems().stream().anyMatch(cartItem1 -> cartItem1.getProduct().getId().equals(cartItemDTO.getProductId()))) {
            throw new DuplicateEntityException("This product has already been added to cart");
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setProduct(productById);
        cart.getCartItems().add(cartItem);
        return cartRepo.save(cart);
    }

    public Cart updateCartItemQuantity(CartItemDTO cartItemDTO, Long customerId, Long cartItemId) {
        // check if cart exists
        Cart cart = getCartByCustomerId(customerId);
        CartItem cartItemById = cartItemService.getCartItemByIdAndCustomerId(cartItemId, customerId);
        // check if product exists
        Product productById = productService.getProductById(cartItemDTO.getProductId());

        if (cartItemDTO.getQuantity() < 1) {
            throw new InvalidInputException("Quantity can't be less than 1");
        }

        if (productById.getStock() < cartItemDTO.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock requested.");
        }

//        if (cart.getCartItems().stream().noneMatch(cartItem1 -> cartItem1.getId().equals(cartItemId))) {
//            throw new DuplicateEntityException("This product does not exist in the cart");
//        }

        cartItemById.setCart(cart);
        cartItemById.setQuantity(cartItemDTO.getQuantity());

        cartItemService.updateCartItem(cartItemById, cartItemId);
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

        CartItem cartItem = cartItemService.getCartItemByIdAndCustomerId(cartItemId, customerId);

        cartItemService.deleteCartItemById(cartItemId);
        return cart;
    }

    public Cart updateCartAddress(String address, Long customerId) {
        // check if cart exists
        Cart cart = getCartByCustomerId(customerId);
        if (!(cart != null && cart.getAddress() != null && !cart.getAddress().isEmpty()))
        {
            throw new AddressNotFoundException("Adress Not Found");
        }

        cart.setAddress(address);
        cartRepo.save(cart);
        return cart;
    }
}
