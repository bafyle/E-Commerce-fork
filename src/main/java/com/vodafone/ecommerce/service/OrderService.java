package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.exception.*;
import com.vodafone.ecommerce.model.*;
import com.vodafone.ecommerce.repository.OrderRepo;
import com.vodafone.ecommerce.util.PaymentUtil;
import com.vodafone.ecommerce.util.ValidationCardUtil;
import com.vodafone.ecommerce.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    private final OrderItemService orderItemService;

    private final CustomerService customerService;

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepo orderRepo, CustomerService customerService, CartService cartService,
                        OrderItemService orderItemService, ProductService productService) {
        this.orderRepo = orderRepo;
        this.customerService = customerService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.productService = productService;
    }

    public List<Order> getAllOrdersByCustomerId(Long customerId) {
        return orderRepo.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    public Order getOrderById(Long customerId, Long orderId) {
        Optional<Order> order = orderRepo.findByIdAndCustomerId(orderId, customerId);

        if (order.isEmpty()) {
            throw new NotFoundException("Order not found");
        }

        return order.get();
    }

    public Order checkoutCart(Long customerId, Card card) {
        if (card.getCardNumber().trim().length() < 16) {
            throw new InvalidInputException("Card number must be 16 digits");
        }
        if (!ValidationUtil.isNumeric(card.getCardNumber())) {
            throw new InvalidInputException("Card number must only contain digits");
        }
        if (!ValidationUtil.isNumeric(card.getExpirationMonth())) {
            throw new InvalidInputException("Card expiration month must only contain digits");
        }

        if (card.getExpirationMonth().trim().length() == 0)
        {
            throw new InvalidInputException("Card expiration month must be valid digit(s)");
        }
        if (card.getExpirationMonth().trim().length() == 1 && Integer.parseInt(card.getExpirationMonth()) <= 9) {
            card.setExpirationMonth("0" + card.getExpirationMonth());
        }

        if (card.getExpirationMonth().trim().length() > 2)
        {
            throw new InvalidInputException("Card expiration month must be 2 digits");
        }

        if (card.getExpirationYear().trim().length() != 4) {
            throw new InvalidInputException("Card expiration year must be 4 digits");
        }
        if (!ValidationUtil.isNumeric(card.getExpirationYear())) {
            throw new InvalidInputException("Card expiration year must only contain digits");
        }
        if (card.getPinNumber().trim().length() < 4) {
            throw new InvalidInputException("Card number must be 4 digits");
        }
        if (!ValidationUtil.isNumeric(card.getPinNumber())) {
            throw new InvalidInputException("Card pin must only contain digits");
        }

        Cart cart = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Order can't be created, Cart is empty");
        }

        Order order = new Order();
        order.setOrderItems(new HashSet<>());
        order.setPaymentMethod("Card");
        for (CartItem cartItem:
              cartItems) {
            Product productById = productService.getProductById(cartItem.getProduct().getId());

          if (cartItem.getProduct().getIsArchived()) {
                continue;
            }
          
            boolean quantityOrderIsSmallerOrEqualsThanStock = cartItem.getQuantity() <= productService.getProductById(cartItem.getProduct().getId()).getStock();
            

            if (!quantityOrderIsSmallerOrEqualsThanStock)
            {
                throw new InsufficientStockException("Quantity Ordered is Less Than What Is Available");

            }


            OrderItem orderItem = new OrderItem();

            if (cartItem.getQuantity() > 10) {
                throw new InvalidInputException("Maximum quantity is 10 items per product");
            }

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            productById.setStock(productById.getStock()-cartItem.getQuantity());
            productService.updateProduct(productById, productById.getId());

            order.getOrderItems().add(orderItem);
        }

        boolean isValidCard = ValidationCardUtil.validateCard(card.getCardNumber(),card.getPinNumber(),card.getExpirationMonth(),card.getExpirationYear());
        if (!isValidCard)
            throw new InvalidCardException("Wrong Card Credential");

        double totalPrice = 0;
        for (CartItem cartItem : cartItems)
        {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        boolean canFulfilPayment = PaymentUtil.canFulfilOrder(new PaymentRequest(card.getCardNumber(),totalPrice));
        if (!canFulfilPayment)
            throw new InsufficientAmountInCardException("Insufficient Balance within Card");


        cartService.deleteAllCartItems(customerId);
        Customer customer = customerService.getCustomerById(customerId);
        order.setCustomer(customer);

        order.setAddress(cart.getAddress());

        return orderRepo.save(order);
    }

    public Order checkoutCart(Long customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        Set<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Order can't be created, Cart is empty");
        }

        Order order = new Order();
        order.setOrderItems(new HashSet<>());
        order.setPaymentMethod("Cash");
        cartItems.forEach(cartItem -> {
            Product productById = productService.getProductById(cartItem.getProduct().getId());
            boolean quantityOrderIsSmallerOrEqualsThanStock = cartItem.getQuantity() <= productService.getProductById(cartItem.getProduct().getId()).getStock();
            if (cartItem.getProduct().getIsArchived()) {
                throw new InsufficientStockException("Product not available");
            }

            if (!quantityOrderIsSmallerOrEqualsThanStock)
            {
                throw new InsufficientStockException("Quantity Ordered is Less Than What Is Available");

            }

            OrderItem orderItem = new OrderItem();

            if (cartItem.getQuantity() > 10) {
                throw new InvalidInputException("Maximum quantity is 10 items per product");
            }

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());

            order.getOrderItems().add(orderItem);

            productById.setStock(productById.getStock()-cartItem.getQuantity());
            productService.updateProduct(productById, productById.getId());
        });

        cartService.deleteAllCartItems(customerId);

        order.setAddress(cart.getAddress());
        order.setCustomer(customerService.getCustomerById(customerId));

        return orderRepo.save(order);
    }

    public Order updateOrderStatus(Order order, Long customerId, Long orderId) {
        Optional<Order> orderById = orderRepo.findByIdAndCustomerId(orderId, customerId);

        if (order.getStatus().trim().length() < 1) {
            throw new InvalidInputException("Order status can't be empty");
        }

        if (orderById.isEmpty()) {
            throw new NotFoundException("This order is not registered to this customer");
        }

        orderById.get().setStatus(order.getStatus());

        return orderRepo.save(orderById.get());
    }

    public OrderItem updateOrderItemRating(Integer rating, Long customerId, Long orderId, Long orderItemId) {
        Optional<OrderItem> orderItemById = orderItemService.findByIdAndOrderIdAndCustomerId(orderId, customerId, orderItemId);

        if (orderItemById.isEmpty()) {
            throw new NotFoundException("This order is not registered to this customer");
        }

        if (rating < 1 || rating > 5) {
            throw new InvalidInputException("Rating must be between 1 and 5");
        }

        orderItemById.get().setRating(rating);

        OrderItem orderItemRes = orderItemService.updateOrderItemRating(orderItemById.get());

        Double productRating = orderItemService.avgRatingByProductId(orderItemRes.getProduct().getId());

        productRating = Math.round(productRating * 100.0) / 100.0;

        productService.updateProductRating(orderItemRes.getProduct().getId(), productRating);

        return orderItemRes;

    }
}
