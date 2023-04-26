package com.example.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.crud.controller.base.BaseController;
import com.example.crud.entity.Chef;
import com.example.crud.pagination.PageResult;
import com.example.crud.service.ChefService;
import com.example.crud.util.MessageResult;

// all routes here: /api/chef/
public class ChefController extends BaseController { 

    @Autowired
    private ChefService service;
     
    // TASK: USE THE queryDslForPageListResult(QueryDslContext qdc, Integer pageNo, Integer pageSize) OF THIS SIGNATURE 

    // get All chefs (paginated)
    @GetMapping("page")
    public MessageResult page(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageResult<Chef> pageResult = service.allActiveChefs(pageNo, pageSize);
        return success(pageResult);
    }
}
 