package com.example.crud.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.controller.base.BaseController;
import com.example.crud.entity.Chef;
import com.example.crud.entity.Eatery;
import com.example.crud.pagination.PageResult;
import com.example.crud.service.ChefService;
import com.example.crud.service.EateryService;
import com.example.crud.service.helper.LocaleMessageSourceService;
import com.example.crud.util.BindingResultUtil;
import com.example.crud.util.MessageResult; 


// all routes here: /api/chef/
@RestController
@RequestMapping("/api/chef")
public class ChefController extends BaseController { 
    //TODO: upgrade all PageResult to Page 
    // TODO: add permissions org.apache.shiro.authz.annotation.RequiresPermissions


    @Autowired
    private ChefService service;

    @Autowired
    private EateryService eateryService;

    @Autowired
    private LocaleMessageSourceService messageSource;

    // get All chefs (paginated)
   

    @GetMapping("active") 
    public MessageResult pageActive(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        // error been caused by the fact that I added eatery in some chefs
        PageResult<Chef> pageResult = service.allActiveChefs(pageNo, pageSize); 
        
        return success(pageResult);
    } 

    @GetMapping
    public MessageResult pageAll(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageResult<Chef> pageResult = service.allChefs(pageNo, pageSize);

        return success(pageResult);
    } 

    //get all by eatery id paginated
    @GetMapping("eatry")
    public MessageResult page(
            @RequestParam(value = "pageNo", defaultValue = "1") Long eateryId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<Chef> pageResult = service.findAllByEateryId(eateryId, pageNo, pageSize);
        return success(pageResult);
    }

    @GetMapping("{id}")
    public MessageResult getChefById(@PathVariable("id") Long id) {
        Optional<Chef> chef = service.findByIdUingJPA(id);
        if(!chef.isPresent()){
            return error("Invalid Id");
        }
        return success(chef.get());
    }

    @PostMapping
    // @Valid ??
    public MessageResult addChef(@RequestBody Chef chef, BindingResult bindingResult){
        MessageResult result = BindingResultUtil.validate(bindingResult);
        if (result != null) { 
            // System.out.println();
            return result; 
        }
        Chef one = service.findByUsername(chef.getUsername());
        if (one != null) { 
            return error(messageSource.getMessage("CHEF EXIST"));
        } 
        service.save(chef);
        return success(); 
    }

    // add chef to eatery
    @PostMapping("addEatery")
    public MessageResult addChefToEatery(@RequestParam Integer eateryId, @RequestParam Integer chefId){
        Eatery eatery = eateryService.getById(Long.valueOf(eateryId));
        Optional<Chef> chef = service.findByIdUingJPA(Long.valueOf(chefId));
        if(!chef.isPresent()){
            return error("Invalid Id");
        }
        Chef chef_obj= chef.get();
        Chef new_obj = service.setEatery(chef_obj, eatery);
        return success(new_obj); 
    }



}
 