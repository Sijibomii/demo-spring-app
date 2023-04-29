package com.example.crud.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.dao.EateryDao;
import com.example.crud.entity.Eatery;
import com.example.crud.service.base.BaseService;

@Service
public class EateryService extends BaseService<Eatery, EateryDao>{
    
    @Autowired
    protected EateryDao dao; 

    public Eatery save(Eatery eatery) {
        return dao.save(eatery);
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
    
}
