package com.example.crud.entity;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import lombok.Data;
import com.example.crud.constants.OrderStatus;

@Entity
@Data
@Table
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    // eatery
    @ManyToOne
    @NotNull
    private Eatery eatery;

    // customer
    @NotNull
    private Long customerId;

    // Price
    private double price;
}
