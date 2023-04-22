package com.example.crud.service.base;

import org.springframework.stereotype.Component;
import lombok.Setter;
import com.example.crud.dao.base.BaseDao;
import java.util.*;
import java.io.Serializable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
// E is the entity. BaseDao takes in the entity on a norms
public class BaseService<E, D extends BaseDao<E>> {
    
    @Setter
    protected D dao;

    @Autowired
    protected EntityManager entityManager;

    // public Optional<E> findById(Long id) {

    //     // CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    //     // CriteriaQuery query = cb.createQuery();

    //     // Root<E> root = query.from(E.class)

    //     // return dao.findOne(idSpecification);
    // }

    public List<E> findAll() {
        return dao.findAll();
    }

//    service.findById(UserSpeci)
}
