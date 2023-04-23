package com.example.crud.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MealDto {
    
    private String name;

    private double price;

    private List<ChefDto> chefs;
}
