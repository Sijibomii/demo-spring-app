package com.example.crud.service.base;

import org.springframework.stereotype.Component;
import lombok.Setter;
import com.example.crud.dao.base.BaseDao;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.example.crud.pagination.PageResult;
import com.example.crud.pagination.PageListMapResult;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.core.Tuple;
@Component
// E is the entity. BaseDao takes in the entity on a norms
public class BaseService<E, D extends BaseDao<E>> {
    
    @Setter
    protected D dao;

    @Autowired
    protected EntityManager entityManager;

    // Factory class for query and DML clause creation
    @Autowired
    protected JPAQueryFactory queryFactory;


    /**
     * query list
     *
     * @param pageNo                pagination parameter
     * @param pageSize              Paging Size
     * @param predicateList        Query conditions
     * @param entityPathBase         query form
     * @param orderSpecifierList    Sort by
     * @return      
     */
    @Transactional(readOnly = true)
    @SuppressWarnings({ "all" })
    //  QAnnouncement.announcement is a generated Q type querydsl generates for our classes
   
    //predicates.add(QAnnouncement.announcement.isShow.eq(true)) -> example of queryDsl predicate

    // orderSpecifiers.add(QAnnouncement.announcement.createTime.desc()); -> example of predicates added (queryDsl adds this for all our entities and all their fields)
    public PageResult<E> queryDsl(Integer pageNo, Integer pageSize, List<Predicate> predicateList, EntityPathBase<E> entityPathBase, List<OrderSpecifier<?>> orderSpecifierList) {
        List<E> list;
        // query form
        // Create a new JPQLQuery instance with the given source and projection
        // JPAQuery is the default implementation of the JPQLQuery interface for JPA
        // SELECT * FREOM entityPathBase
        JPAQuery<E> jpaQuery = queryFactory.selectFrom(entityPathBase);
        //  Query conditions
        if (predicateList != null && predicateList.size() > 0) {
            // toArray here an empty array with size set. it converts predicateList to Array
            // new Predicate[predicateList.size()] specifies the type of arr to be returned by predicateList.toArray
            jpaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        }
        //
        if (orderSpecifierList != null && orderSpecifierList.size() > 0) {
            jpaQuery.orderBy(orderSpecifierList.toArray(new OrderSpecifier[orderSpecifierList.size()]));
        }
       
        //
        if (pageNo != null && pageSize != null) {
            // filter by page number and page size
            list = jpaQuery.offset((pageNo - 1) * pageSize).limit(pageSize).fetch();
        } else {
            list = jpaQuery.fetch();
        }
        // System.out.println(list);
        return new PageResult<>(list, pageNo, pageSize, jpaQuery.fetchCount());
    }

     /**
     *     Query a single entity using one predicate
     *
     * @param predicate      Query conditions
     * @param entityPathBase query form
     * @return
     */
    @Transactional(readOnly = true)
    public E queryOneDsl(Predicate predicate, EntityPathBase<E> entityPathBase) {
        return queryFactory.selectFrom(entityPathBase).where(predicate).fetchFirst();
    }

    //  Multi-table joint query
    /**
     * @param expressions        query list
     * @param entityPaths        query form
     * @param predicates         condition
     * @param orderSpecifierList to sort
     * @param pageNo             page number
     * @param pageSize           page size
     */
    @SuppressWarnings({ "all" }) // suppress for now
    @Transactional(readOnly = true)
    public PageListMapResult queryDslForPageListResult(
        // Expression defines a general typed expression in a Query instance. The generic type parameter is a reference to the type the expression is bound to.
        // query.from(employee).select(employee.firstName, employee.lastName).fetch()
            List<Expression> expressions,
            List<EntityPath> entityPaths, // multi table
            List<Predicate> predicates,
            List<OrderSpecifier> orderSpecifierList,
            Integer pageNo,
            Integer pageSize) {
               
        JPAQuery<Tuple> jpaQuery = queryFactory.select(expressions.toArray(new Expression[expressions.size()]))
                .from(entityPaths.toArray(new EntityPath[entityPaths.size()]))
                .where(predicates.toArray(new Predicate[predicates.size()]));
        List<Tuple> tuples = jpaQuery.orderBy(orderSpecifierList.toArray(new OrderSpecifier[orderSpecifierList.size()]))
                .offset((pageNo - 1) * pageSize).limit(pageSize)
                .fetch();
        List<Map<String, Object>> list = new LinkedList<>();// return result
        // Package result
        for (int i = 0; i < tuples.size(); i++) {
            //tuples        Traversing tuples
            Map<String, Object> map = new LinkedHashMap<>();// a message
            for (Expression expression : expressions) {
                map.put(expression.toString().split(" as ")[1],//   Name as Key
                        tuples.get(i).get(expression));// get results
            }
            list.add(map);
        }
        PageListMapResult pageListMapResult = new PageListMapResult(list, pageNo, pageSize, jpaQuery.fetchCount());//分页封装
        return pageListMapResult;
    }

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
