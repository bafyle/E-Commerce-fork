package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.OrderItem;
import com.vodafone.ecommerce.repository.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepo orderItemRepo;

    @Autowired
    public OrderItemService(OrderItemRepo orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }


    public Optional<OrderItem> findByIdAndOrderIdAndCustomerId(Long orderId, Long customerId, Long orderItemId) {
        return orderItemRepo.findByIdAndOrderIdAndCustomerId(orderItemId, orderId, customerId);
    }

    public OrderItem updateOrderItemRating(OrderItem orderItem) {
        return orderItemRepo.save(orderItem);
    }
}
