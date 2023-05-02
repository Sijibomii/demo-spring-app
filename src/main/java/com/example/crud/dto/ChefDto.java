package com.example.crud.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChefDto {
    
    private long id;

    private String name;

    private String username;

    private EateryDto eatery;

    private List<MealDto> meals;

    private boolean is_male;

    private boolean is_on_duty;
    
    private boolean is_active;
    
}
