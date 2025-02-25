package com.springtraining.order.controller;

import com.springtraining.order.model.Order;
import com.springtraining.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Order creation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Order already created"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestParam Long customerId, @RequestParam Double totalAmount) {
        Order order = orderService.save(customerId,totalAmount);
        return (order.getOrderId()>0) ? ResponseEntity.ok(order) : ResponseEntity.badRequest().body(order);
    }
}
