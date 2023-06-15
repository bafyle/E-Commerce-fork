package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.*;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Order History
// Checkout Page
// Card Number Page

@Controller
@RequestMapping("/customer/{customerId}/order")
@PreAuthorize("hasAuthority('Customer')")

public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;


    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping
    public String getCurrentOrdersByCustomerId(@PathVariable("customerId") Long customerId,
                                           @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Cart order = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = order.getCartItems();
        model.addAttribute("customerId", customerId);
        model.addAttribute("cart_items", cartItems);
        return "customer-orderCurrentCartRead";
    }

    @GetMapping("/history")
    public String getAllOrdersByCustomerId(@PathVariable("customerId") Long customerId,
                                           @AuthenticationPrincipal SecurityUser user, Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        List<Order> allOrders = orderService.getAllOrdersByCustomerId(customerId);
        model.addAttribute("customerId", customerId);
        model.addAttribute("all_orders", allOrders);
        return "customer-orderReadAllOrders";
    }

    @GetMapping("/{orderId}")
    public String getOrderById(@PathVariable("customerId") Long customerId,
                               @PathVariable("orderId") Long orderId,
                               @AuthenticationPrincipal SecurityUser user,
                               Model model) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        Order order = orderService.getOrderById(customerId,orderId);
        Set<OrderItem> orderItems = order.getOrderItems();
        model.addAttribute("customerId", customerId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("order",order);
        model.addAttribute("order_items",orderItems);
        return "customer-orderReadSingleOrder";
    }



    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable("customerId") Long customerId,
                                                   @PathVariable("orderId") Long orderId,
                                                   @RequestBody Order order,
                                                   @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return new ResponseEntity<>(orderService.updateOrderStatus(order, customerId, orderId), HttpStatus.OK);
    }

    @PutMapping("/{orderId}/{orderItemId}")
    public ResponseEntity<OrderItem> updateOrderItemRating(@PathVariable("customerId") Long customerId,
                                                           @PathVariable("orderId") Long orderId,
                                                           @PathVariable("orderItemId") Long orderItemId,
                                                           @RequestBody OrderItem orderItem,
                                                           @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return new ResponseEntity<>(orderService.updateOrderItemRating(orderItem, customerId, orderId, orderItemId), HttpStatus.OK);
    }
}
