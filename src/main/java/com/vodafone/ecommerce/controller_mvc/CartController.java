package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Cart;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.model.dto.CartItemDTO;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/customer/{customerId}/cart")
@PreAuthorize("hasAuthority('Customer')")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getCartById(@PathVariable(name = "customerId") Long customerId,
                              @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Cart cart = cartService.getCartByCustomerId(customerId);
     //   Set<CartItem> cartItems = cart.getCartItems().stream().filter(item -> !item.getProduct().getIsArchived()).collect(Collectors.toSet());;
        Set<CartItem> cartItems = cart.getCartItems();

        CartItemDTO cartItemDTO = new CartItemDTO();

        model.addAttribute("customerId", customerId);
        model.addAttribute("cart_items", cartItems);
        return "customer-readCartList";
    }

    @PostMapping
    public String addCartItem(@PathVariable(name = "customerId") Long customerId,
                              @Valid @ModelAttribute CartItemDTO cartItemDTO,
                              @AuthenticationPrincipal SecurityUser user,
                              BindingResult bindingResult,
                              Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());

        if (bindingResult.hasErrors()) {
            Cart cart = cartService.getCartByCustomerId(customerId);
            Set<CartItem> cartItems = cart.getCartItems();
            model.addAttribute("customerId", customerId);
            model.addAttribute("cart_items", cartItems);
            return "customer-readCartList";
        }

        Cart cartRes = cartService.addCartItem(customerId, cartItemDTO);
        return "redirect:/customer/{customerId}/cart";
    }

    @PostMapping(value = "/{cartItemId}/update")
    public String updateCartItem(@PathVariable(name = "customerId") Long customerId,
                                 @PathVariable(name = "cartItemId") Long cartItemId,
                                 @Valid @ModelAttribute("cartItemDTO") CartItemDTO cartItem,
                                 @AuthenticationPrincipal SecurityUser user,
                                 BindingResult bindingResult, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());


        if (bindingResult.hasErrors()) {
            Cart cart = cartService.getCartByCustomerId(customerId);
            Set<CartItem> cartItems = cart.getCartItems();
            model.addAttribute("customerId", customerId);
            model.addAttribute("cart_items", cartItems);
            return "customer-readCartList";
        }

        Cart cartRes = cartService.updateCartItemQuantity(cartItem, customerId, cartItemId);
        Cart cart = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = cart.getCartItems();
        model.addAttribute("customerId", customerId);
        model.addAttribute("cart_items", cartItems);
        return "redirect:/customer/{customerId}/cart";
    }

    @DeleteMapping
    public ResponseEntity<Cart> deleteCart(@PathVariable(name = "customerId") Long customerId,
                                           @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return new ResponseEntity<>(cartService.deleteAllCartItems(customerId), HttpStatus.OK);
    }

    @GetMapping(value = "/{cartItemId}/delete")
    public String deleteCartItem(@PathVariable(name = "customerId") Long customerId,
                                 @PathVariable(name = "cartItemId") Long cartItemId,
                                 @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        cartService.deleteCartItem(customerId, cartItemId);
        return "redirect:/customer/{customerId}/cart";
    }




//    @PostMapping
//    public String checkoutCart(@PathVariable("customerId") Long customerId,
//                               @AuthenticationPrincipal SecurityUser user,
//                               BindingResult bindingResult, Model model){
//        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
//
//        if (bindingResult.hasErrors()) {
//            Cart cart = cartService.getCartByCustomerId(customerId);
//            Set<CartItem> cartItems = cart.getCartItems().stream().filter(item -> !item.getProduct().getIsArchived()).collect(Collectors.toSet());;
//            model.addAttribute("customerId", customerId);
//            model.addAttribute("cart_items", cartItems);
//            return "customer-readCartList";
//        }
//        Cart cart = cartService.getCartByCustomerId(customerId);
//        Set<CartItem> cartItems = cart.getCartItems().stream().filter(item -> !item.getProduct().getIsArchived()).collect(Collectors.toSet());;
//
//        model.addAttribute("customerId", customerId);
//        model.addAttribute("cart_items", cartItems);
//        return "redirect:/customer/"+customerId+"/order/address";
//    }

//    @PostMapping
//    public ResponseEntity<Order> checkoutCart(@PathVariable("customerId") Long customerId,
//                                              @AuthenticationPrincipal SecurityUser user) {
//        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
//        return new ResponseEntity<>(/*orderService.checkoutCart(customerId)*/null, HttpStatus.CREATED);
//    }

}
