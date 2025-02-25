package com.springtraining.order.service;

import com.springtraining.order.model.Order;
import com.springtraining.order.model.OrderStatus;

import java.util.Optional;

public interface OrderService {

    Order save(Long customerId, Double totalAmount);

    Optional<Order> findById(Long orderId);

}
