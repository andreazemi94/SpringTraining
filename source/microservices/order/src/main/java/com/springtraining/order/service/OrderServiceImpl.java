package com.springtraining.order.service;

import com.springtraining.order.kafka.KafkaProducer;
import com.springtraining.order.model.Order;
import com.springtraining.order.model.OrderStatus;
import com.springtraining.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @Override
    public Optional<Order> save(Order order) {
        if(orderRepository.findById(order.getOrderId()).isPresent())
            return Optional.empty();
        return Optional.of(create(order));

    }

    private Order create(Order order){
        order.setStatus(OrderStatus.PROCESSING);
        log.info("Set order status: {}", order);
        order.setOrderDate(new Date());
        log.info("Set order date: {}", order);
        orderRepository.save(order);
        log.info("Order created successfully in the database: {}", order);
        kafkaProducer.sendMessage(order);
        return order;
    }

    @Transactional
    @Override
    public void updateStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        log.info("Set order status: {}", order);
        save(order);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

}
