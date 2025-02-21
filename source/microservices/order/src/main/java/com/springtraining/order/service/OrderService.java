package com.springtraining.order.service;

import com.springtraining.order.model.Order;
import com.springtraining.order.model.OrderStatus;

import java.util.Optional;

public interface OrderService {

    Optional<Order> save(Order order);

    void updateStatus(Order order, OrderStatus status);

    Optional<Order> findById(Long orderId);

}
