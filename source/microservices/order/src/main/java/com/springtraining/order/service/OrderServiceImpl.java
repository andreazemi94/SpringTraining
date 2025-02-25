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
    public Order save(Long customerId, Double totalAmount) {
        return create(customerId,totalAmount);
    }

    private Order create(Long customerId, Double totalAmount){
        Order order = orderRepository.save(Order.builder().build());
        order.setCustomerId(customerId);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PROCESSING);
        order.setTotalAmount(totalAmount);
        log.info("Order created successfully in the database: {}", order);
        kafkaProducer.sendMessage(order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

}
