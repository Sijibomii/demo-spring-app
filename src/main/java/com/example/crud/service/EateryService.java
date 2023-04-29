package com.example.crud.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.dao.EateryDao;
import com.example.crud.dto.EateryFullDto;
import com.example.crud.entity.Eatery;
import com.example.crud.entity.QChef;
import com.example.crud.entity.QEatery;
import com.example.crud.pagination.PageListMapResult;
import com.example.crud.service.base.BaseService;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

@Service
public class EateryService extends BaseService<Eatery, EateryDao>{
    
    @Autowired
    protected EateryDao dao; 

    public Eatery save(Eatery eatery) {
        return dao.save(eatery);
    }

    public Eatery getById(Long id){
        Boolean exist = dao.existsById(id);
        if(exist){
            return dao.getReferenceById(id);
        }
        return null;
    }

    public Eatery findByName(String name){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Eatery> query = builder.createQuery(Eatery.class);
        Root<Eatery> eatery = query.from(Eatery.class);

        query.where(builder.equal(eatery.get("name"), name));
        List<Eatery> eatery_list = entityManager
            .createQuery(query)
            .getResultList();
        if (eatery_list.size() < 1){
            return null;
        }
        return eatery_list.get(0);
    }

    //TODO: add pagination
    // EateryFullDto
    public PageListMapResult getEateryDetailsById(Long id){
        List<Expression> expressions = new ArrayList<>();
        expressions.add(QEatery.eatery.name.as("name"));
        expressions.add(QEatery.eatery.address.as("address"));
        // leave manager out now
        // expressions.add(QEatery.eatery.manager.as("manager"));
        expressions.add(QChef.chef.username.as("username"));
        expressions.add(QChef.chef.salary.as("salary"));
   
        List<EntityPath> entityPaths = new ArrayList<>();
        entityPaths.add(QChef.chef);
        entityPaths.add(QEatery.eatery);
        // // predicates: https://www.youtube.com/watch?v=pYx__ixuxGk
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QChef.chef.eatery.id.eq(QEatery.eatery.id));   
        predicates.add(QChef.chef.eatery.id.eq(id)); 
        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();
        orderSpecifierList.add(QEatery.eatery.id.desc());
        PageListMapResult pageListMapResult = super.queryDslForPageListResult(expressions, entityPaths, predicates, orderSpecifierList, Integer.valueOf(1), Integer.valueOf(10));
        
        return pageListMapResult;
    }
    
}
