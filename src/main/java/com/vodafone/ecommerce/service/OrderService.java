package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.Address;
import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    private final CustomerService customerService;

    @Autowired
    public OrderService(OrderRepo orderRepo, CustomerService customerService) {
        this.orderRepo = orderRepo;
        this.customerService = customerService;
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

    public Order addOrder(Order order, Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);

        // TODO: validate on creation date?

        order.setCustomer(customer);
        return orderRepo.save(order);
    }

    public Order updateOrder(Order order, Long customerId, Long orderId) {
        Customer customer = customerService.getCustomerById(customerId);

        if (customer.getOrders().stream().noneMatch(order1 -> order1.getId().equals(orderId))) {
            throw new NotFoundException("This order is not registered to this customer");
        }

        order.setId(orderId);
        order.setCustomer(customer);

        return orderRepo.save(order);
    }

    public void deleteOrder(Long customerId, Long orderId) {
        Optional<Order> order = orderRepo.findByIdAndCustomerId(orderId,customerId);

        if (order.isEmpty()) {
            throw new NotFoundException("This order is not registered to this customer");
        }

        orderRepo.delete(order.get());
    }
}
