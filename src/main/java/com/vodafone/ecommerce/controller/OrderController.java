package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

// Order History
// Checkout Page
// Card Number Page

@RestController
@RequestMapping("customer/{customerId}/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Set<Order>> getAllOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersByCustomerId(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("customerId") Long customerId, @PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(customerId, orderId));
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@PathVariable("customerId") Long customerId, @RequestBody Order order) {
        return new ResponseEntity<>(orderService.addOrder(order, customerId), HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("customerId") Long customerId,
                                             @PathVariable("orderId") Long orderId,
                                             @RequestBody Order order) {
        return new ResponseEntity<>(orderService.updateOrder(order, customerId, orderId), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("customerId") Long customerId,
                                             @PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId, customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
