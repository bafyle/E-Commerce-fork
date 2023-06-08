package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Customer;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.repository.CustomerRepo;
import com.vodafone.ecommerce.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CustomerRepo customerRepo;

    public List<Order> getAllOrders() {
        Customer c = new Customer();
        Customer save = customerRepo.save(c);
        Order order = new Order();
        order.setCustomer(save);
        orderRepo.save(order);
        return orderRepo.findAll();
    }
}
