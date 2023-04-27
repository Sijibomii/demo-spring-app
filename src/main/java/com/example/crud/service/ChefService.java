package com.example.crud.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.crud.entity.Chef;
import com.example.crud.entity.Eatery;
import com.example.crud.entity.Meal;
import com.example.crud.entity.QChef;
import com.example.crud.entity.QEatery;
import com.example.crud.constants.PageModel;
import com.example.crud.dao.ChefDao;
import com.example.crud.service.base.BaseService;
import lombok.Setter;
import java.util.Optional;
import com.example.crud.dao.ChefDao.specs;
import javax.persistence.criteria.*;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.example.crud.pagination.PageResult;
import com.example.crud.pagination.Restrictions;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import com.example.crud.pagination.Criteria;
import com.example.crud.pagination.PageListMapResult;

import org.springframework.data.domain.PageRequest;


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

    public Chef findByUsername(String username){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chef> query = builder.createQuery(Chef.class);
        Root<Chef> chef = query.from(Chef.class);

        query.where(builder.equal(chef.get("username"), username));
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
    public PageResult<Chef> allActiveChefs(Integer pageNo, Integer pageSize){
        List<Predicate> predicates = new ArrayList<>(); 
        predicates.add(QChef.chef.is_active.eq(true));

        //specify ordering
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        orderSpecifiers.add(QChef.chef.createdOn.desc());

        PageResult<Chef> pageResult = queryDsl(pageNo, pageSize, predicates, QChef.chef, orderSpecifiers);
        return pageResult;
    }


    // use criteria classes(pagination to create queries)
    // this is a flexible method expressionlists can be added and converted intoa jpa critaria stuff
    // PAGE RESULT
    @SuppressWarnings({ "deprecation" })
    @Transactional(readOnly = true)
    public PageResult<Chef> query(List<BooleanExpression> booleanExpressionList, Integer pageNo, Integer pageSize) {
        List<Chef> list;
        JPAQuery<Chef> jpaQuery = (JPAQuery<Chef>) queryFactory.selectFrom(QChef.chef); 
        if (booleanExpressionList != null) {
            jpaQuery.where(booleanExpressionList.toArray(new BooleanExpression[booleanExpressionList.size()]));
        }
        if (pageNo != null && pageSize != null) {
            list = jpaQuery.offset((pageNo - 1) * pageSize).limit(pageSize).fetch();
        } else {
            list = jpaQuery.fetch();
        }
        return new PageResult<>(list, jpaQuery.fetchCount());
    }

    public Page<Chef> pageQuery(Integer pageNo, Integer pageSize) {
        //Criteria.sort("id","createTime.desc") ) // The parameter entity class is the field name
        Sort orders = Criteria.sortStatic("salary");
        //pagination parameter
        Pageable pageRequest = PageRequest.of(pageNo, pageSize, orders);
        
        //Query conditions
        // here, no conditions so select all
        Criteria<Chef> specification = new Criteria<Chef>();
        return dao.findAll(specification, pageRequest);
    }
        
    ///MODIFY LARGE CHUNKS OF DATA!!!
    @Transactional(rollbackFor = Exception.class)
    // take in set salary type
    public void setSalaries(double salary, Eatery eatery) {
        List<Chef> list = dao.findAll();
        // filter produces a new stream that contains elements of the original stream that pass a given test (specified by a Predicate).
        list.stream().filter(x ->
                !x.getEatery().equals(eatery)
        ).forEach(x -> {
           dao.save(x);
        });
    }

    // EXAMPLE USING RESTRICTION 
    public Page<Chef> findAllByEateryId(Long eateryId, int pageNo, int pageSize){
    	Sort orders = Criteria.sortStatic("salary.desc");
        // pagination parameters
        Pageable pageRequest = PageRequest.of(pageNo - 1, pageSize, orders);
        // Query conditions
        Criteria<Chef> specification = new Criteria<Chef>();
        specification.add(Restrictions.eq("eateryId", eateryId, false));
         
        return dao.findAll(specification, pageRequest);
    }
    
    public Page<Chef> findAllByEateryAndMealId(int pageNo, int pageSize, long eateryId, long mealId) {
        Sort orders = Criteria.sortStatic("salary.desc");
        //pagination parameter. PageRequest implements Pageable
        PageRequest pageRequest = PageRequest.of( - 1, pageSize, orders);
        //Query conditions
        Criteria<Chef> specification = new Criteria<Chef>();
        specification.add(Restrictions.eq("eateryId", eateryId, false));
        specification.add(Restrictions.eq("mealId", mealId, false));
        return dao.findAll(specification, pageRequest);
	}
    
    
    // HOW ENTITY PATH AND EXPRESSION ARE BEEN USED. HOW queryDslForPageListResult IS BEEN USED(CHECK BASESERVICE)
    @SuppressWarnings({"all"})
    @Transactional(readOnly = true)
    public void test() {
        // query field
        // it refused to take Expression so I had to sprecify a string expression
        // remeber to import correctly not Expression and Predicate are from com.querydsl.core.types. not Java.util
        List<Expression> expressions = new ArrayList<>();
        expressions.add(QChef.chef.eatery.as("id"));
   
        List<EntityPath> entityPaths = new ArrayList<>();
        entityPaths.add(QChef.chef);
        entityPaths.add(QEatery.eatery);
        // predicates: https://www.youtube.com/watch?v=pYx__ixuxGk
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QChef.chef.eatery.eq(QEatery.eatery));

        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();
        orderSpecifierList.add(QChef.chef.id.desc());
        PageListMapResult pageListMapResult = super.queryDslForPageListResult(expressions, entityPaths, predicates, orderSpecifierList, Integer.valueOf(1), Integer.valueOf(10));
        System.out.println(pageListMapResult);
    }

    // using boolean expression
    @SuppressWarnings({"all"})
    public Page findAllChefsOnDuty(PageModel pageModel) {
        BooleanExpression eq = QChef.chef.is_on_duty.eq(true);
        return dao.findAll(eq, pageModel.getPageable());
    }
    // use the criteria class. that converts diff restrictions into predicates
    @SuppressWarnings({"all"})
    public Page findAllChefsOnDutyByEatery(Eatery eatery, PageModel pageModel) {
        Criteria<Chef> specification = new Criteria<Chef>();
        specification.add(Restrictions.eq("eatery", eatery, false));
        specification.add(Restrictions.eq("is_on_duty", true, false));
        return dao.findAll(specification, pageModel.getPageable());
    }

    // use projections to use booleanExpressions
    @SuppressWarnings({"all"})
    public Page<Chef> page(List<BooleanExpression> predicates, PageModel pageModel){
        JPAQuery<Chef> query = queryFactory.select(
                // projections help to define the columns on the entity that are returned and helps to prevent overhead and unnecessary queries
                // https://www.youtube.com/watch?v=sLo6if5puC8
                // https://dzone.com/articles/jpa-querydsl-projections, https://vard-lokkur.blogspot.com/2013/05/jpa-basic-projections.html
                Projections.fields(Chef.class,
                        QChef.chef.id.as("id"),
                        QChef.chef.salary.as("salary"),
                        QChef.chef.is_male.as("is_male"),
                        QChef.chef.is_on_duty.as("is_on_duty"),
                        QChef.chef.username.as("username"))
        ).from(QChef.chef).where(predicates.toArray(new BooleanExpression[predicates.size()]));
        // get order by page model
        List<OrderSpecifier> orderSpecifiers = pageModel.getOrderSpecifiers() ;
        query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]));
        long total = query.fetchCount() ;
        query.offset(pageModel.getPageSize()*(pageModel.getPageNo()-1)).limit(pageModel.getPageSize());
        List<Chef> list = query.fetch() ;
        return new PageImpl<Chef>(list,pageModel.getPageable(),total);
    }

    // Blaze stuff start by reading docs

    // window functions 
    // https://vladmihalcea.com/why-you-should-definitely-learn-sql-window-functions/
    // https://www.youtube.com/watch?v=xFeOVIIRyvQ

    // complex queries
}
