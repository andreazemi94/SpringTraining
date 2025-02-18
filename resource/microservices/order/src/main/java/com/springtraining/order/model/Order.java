package com.springtraining.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data @NoArgsConstructor @Builder @AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private Long orderId;
    private Long customerId;
    @JsonIgnore
    private Date orderDate;
    private Double totalAmount;
    @JsonIgnore
    private OrderStatus status;
}
