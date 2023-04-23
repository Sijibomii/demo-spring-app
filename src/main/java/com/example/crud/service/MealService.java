package com.example.crud.service;

import lombok.Setter;
import com.example.crud.entity.Meal;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.crud.dto.MealDto;
import com.example.crud.dao.MealDao;
import com.example.crud.service.base.BaseService;

@Service
// fetching entities only makes sense if you plan to modify them else use a DTO
public class MealService extends BaseService<Meal, MealDao> {
    
    @Setter
    protected MealDao dao;

    // public List<MealDto> findAllMealsAndChefs(){

        
    // }

}
