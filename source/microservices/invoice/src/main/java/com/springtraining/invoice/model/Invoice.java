package com.springtraining.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springtraining.order.model.dto.OrderDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @Builder @AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long invoiceId;
    private Long customerId;
    private Long orderId;
    @JsonIgnore
    private Date invoiceDate;
    private Double totalAmount;

    public static Invoice from(OrderDto orderDto){
        return Invoice.builder()
                .customerId(orderDto.getCustomerId())
                .orderId(orderDto.getOrderId())
                .invoiceDate(new Date())
                .totalAmount(orderDto.getTotalAmount())
                .build();
    }
}
