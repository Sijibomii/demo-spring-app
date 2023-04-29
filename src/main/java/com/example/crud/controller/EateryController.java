package com.example.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.controller.base.BaseController;
import com.example.crud.entity.Eatery;
import com.example.crud.service.EateryService;
import com.example.crud.service.helper.LocaleMessageSourceService;
import com.example.crud.util.BindingResultUtil;
import com.example.crud.util.MessageResult;

@RestController
@RequestMapping("/api/eatery")
public class EateryController extends BaseController {
    @Autowired
    private EateryService service;

    @Autowired
    private LocaleMessageSourceService messageSource;

    @PostMapping
    public MessageResult addEatery(@RequestBody Eatery eatery, BindingResult bindingResult){
        MessageResult result = BindingResultUtil.validate(bindingResult);
        if (result != null) { 
            // System.out.println();
            return result; 
        }
        Eatery one = service.findByName(eatery.getName());
        if (one != null) { 
            return error(messageSource.getMessage("Eatery EXIST"));
        } 
        Eatery ret = service.save(eatery);
        return success(ret); 
    }

    
}
