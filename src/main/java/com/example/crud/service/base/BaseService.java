package com.example.crud.service.base;

import org.springframework.stereotype.Component;
import lombok.Setter;
import com.example.crud.dao.base.BaseDao;
import java.util.*;
// import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
// import javax.persistence.criteria.CriteriaBuilder;
// import javax.persistence.criteria.CriteriaQuery;
// import javax.persistence.criteria.Root;
// import java.io.Serializable;

@Component
// E is the entity. BaseDao takes in the entity on a norms
public class BaseService<E, D extends BaseDao<E>> {
    
    @Setter
    protected D dao;

    @Autowired
    protected EntityManager entityManager;

    public List<E> findAll() {
        return dao.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletes(Long[] ids) {
        for (long id : ids) {
            delete(id);
        }
    } 

    public E save(E e) {
        return (E) dao.save(e);
    }

    public long count(){
        return dao.count();
    }

    


}
