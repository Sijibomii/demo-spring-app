package com.example.crud.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.crud.entity.Chef;
import com.example.crud.entity.Eatery;
import com.example.crud.entity.Meal;
import com.example.crud.dao.ChefDao;
import com.example.crud.service.base.BaseService;
import lombok.Setter;
import java.util.Optional;
import com.example.crud.dao.ChefDao.specs;
import javax.persistence.criteria.*;
import java.util.List;
@Service
public class ChefService extends BaseService<Chef, ChefDao>  {
    
    @Setter
    protected ChefDao dao;

    public Optional<Chef> findByIdUingJPA(long id){
        return findById(specs.byId(id));
    }

    // get chef by id
    // uses JPA metamodel class and specifications
    private Optional<Chef> findById(Specification<Chef> id) {
        // jpa genrated classes 
        return dao.findOne(id);
    }

    //using the criteria api without the meta model class
    public Chef findByName(String name){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chef> query = builder.createQuery(Chef.class);
        Root<Chef> chef = query.from(Chef.class);

        query.where(builder.equal(chef.get("name"), name));
        List<Chef> chef_list = entityManager
            .createQuery(query)
            .getResultList();
        if (chef_list.size() < 1){
            return null;
        }
        return chef_list.get(0);
    }

    // using the criteria api without the meta model class
    public List<Chef> findAllChefsByMealId(long id){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chef> query = builder.createQuery(Chef.class);
        Root<Chef> chef = query.from(Chef.class);
        Join<Chef, Meal> meal = chef.join("meal");
        query.where(builder.equal(meal.get("id"), id));
 
        List<Chef> chefs = entityManager.createQuery(query).getResultList();
        return chefs;
    }

    public List<Chef> findAllByMeal(Meal meal){
        return dao.findAllByMeal(meal);
    }

    public List<Chef> findAllByEatery(Eatery eatery){
        return dao.findAllByEatery(eatery);
    }

    public Chef updateChefSalary(Long id, Double salary){
        Optional<Chef> optionalChef = findByIdUingJPA(id);
        if(!optionalChef.isPresent()){
            return null;
        }
        Chef ch = optionalChef.get();
        ch.setSalary(salary);
        Chef chef  = dao.save(ch);
        return chef;
    }
    // using the @Query annotation (check chef Dao)
    
    // get all pagination
    // use criteria classes(pagination to create queries)

    // complex queries
}
