package com.example.crud.dao;

import com.example.crud.dao.base.BaseDao;
import com.example.crud.entity.Chef;
import com.example.crud.entity.Meal;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;
import com.example.crud.entity.Chef_;
import com.example.crud.entity.Eatery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;



// for some weired reasons this is used as an interface and is never impl in a real class
@Repository
public interface ChefDao extends BaseDao<Chef> {

    // we could use the query method directly. JPA uses the query method name to infer what we mean and write our queries for us
    // this can be used in the service.
    // https://vladmihalcea.com/spring-data-query-methods/#more-26623
    List<Chef> findAllByMeal(Meal meal);

    List<Chef> findAllByEatery(Eatery eatery);


    // using the @Query annotation
    @Query("select c from Chef c where c.meal = :meal and c.is_on_duty= :is_on_duty and c.username like :username and c.salary >= :salary order by createdOn ")
    List<Chef> findAllByMealUsernameSalary(
        @Param("meal") Meal post,
        @Param("is_on_duty") Boolean is_on_duty,
        @Param("username ") String username ,
        @Param("salary") int salary
    );



    interface specs {
        // with JPA metamodel
        static Specification<Chef> byId(Long id) {
            return (root, query, builder) ->
                builder.equal(root.get(Chef_.id), id);
        }
    }
}
