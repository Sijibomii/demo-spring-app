package com.example.crud.dao;

import com.example.crud.dao.base.BaseDao;
import com.example.crud.entity.Chef;
import org.springframework.stereotype.Repository;


// for some weired reasons this is used as an interface and is never impl in a real class
@Repository
public interface ChefDao extends BaseDao<Chef> {
    
}
