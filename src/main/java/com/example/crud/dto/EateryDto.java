package com.example.crud.dto;
import java.util.List;

import com.example.crud.entity.Chef;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EateryDto {
    private Long id;

    private String name;

    private String address;
}
