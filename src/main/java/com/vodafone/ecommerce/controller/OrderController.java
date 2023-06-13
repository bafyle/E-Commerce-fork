package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.OrderItem;
import com.vodafone.ecommerce.model.SecurityUser;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Order History
// Checkout Page
// Card Number Page

@RestController
@RequestMapping("customer/{customerId}/order")
@PreAuthorize("hasAuthority('Customer')")

public class OrderController {
    private final OrderService orderService;


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersByCustomerId(@PathVariable("customerId") Long customerId,
                                                                @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return ResponseEntity.ok(orderService.getAllOrdersByCustomerId(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("customerId") Long customerId,
                                              @PathVariable("orderId") Long orderId,
                                              @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return ResponseEntity.ok(orderService.getOrderById(customerId, orderId));
    }

    @PostMapping
    public ResponseEntity<Order> checkoutCart(@PathVariable("customerId") Long customerId,
                                              @AuthenticationPrincipal SecurityUser user) {
        AuthUtil.isNotLoggedInUserThrowException(customerId, user.getUser().getId());
        return new ResponseEntity<>(orderService.checkoutCart(customerId), HttpStatus.CREATED);
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
