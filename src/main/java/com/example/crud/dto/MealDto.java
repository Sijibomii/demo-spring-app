package com.example.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealDto {
    
    private Long id;

    private double price;

    private String name;

    private boolean isAvailable;
}
