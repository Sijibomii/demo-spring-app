package com.example.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChefDto {
    
    private String name;

    private String username;

    // list of meal dto
}
