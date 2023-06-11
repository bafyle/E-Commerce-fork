package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.EmptyCartException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.*;
import com.vodafone.ecommerce.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    private final OrderItemService orderItemService;

    private final CustomerService customerService;

    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepo orderRepo, CustomerService customerService, CartService cartService, OrderItemService orderItemService) {
        this.orderRepo = orderRepo;
        this.customerService = customerService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
    }

    public Set<Order> getAllOrdersByCustomerId(Long customerId) {
//        return orderRepo.findByCustomerId(customerId);
        Customer customer = customerService.getCustomerById(customerId);

        return customer.getOrders();
    }

    public Order getOrderById(Long customerId, Long orderId) {
        Optional<Order> order = orderRepo.findByIdAndCustomerId(orderId, customerId);

        if (order.isEmpty()) {
            throw new NotFoundException("Order not found");
        }

        return order.get();
    }

    public Order checkoutCart(Long customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Order can't be created, Cart is empty");
        }

        // TODO: handle payment

        Order order = new Order();
        order.setOrderItems(new HashSet<>());

        cartItems.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());

            order.getOrderItems().add(orderItem);
        });

        cartService.deleteAllCartItems(customerId);

        order.setCustomer(customerService.getCustomerById(customerId));

        return orderRepo.save(order);
    }

    public Order updateOrderStatus(Order order, Long customerId, Long orderId) {
        Optional<Order> orderById = orderRepo.findByIdAndCustomerId(orderId, customerId);

        if (orderById.isEmpty()) {
            throw new NotFoundException("This order is not registered to this customer");
        }

        orderById.get().setStatus(order.getStatus());

        return orderRepo.save(orderById.get());
    }

    public OrderItem updateOrderItemRating(OrderItem orderItem, Long customerId, Long orderId, Long orderItemId) {
        Optional<OrderItem> orderItemById = orderItemService.findByIdAndOrderIdAndCustomerId(orderId, customerId, orderItemId);

        if (orderItemById.isEmpty()) {
            throw new NotFoundException("This order is not registered to this customer");
        }

        orderItemById.get().setRating(orderItem.getRating());

        return orderItemService.updateOrderItemRating(orderItemById.get());

    }
}
