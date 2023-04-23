package com.example.crud.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Customer {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String address;

    private String name;
}   
