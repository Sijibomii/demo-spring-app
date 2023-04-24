package com.example.crud.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.crud.entity.Chef;
import com.example.crud.entity.Eatery;
import com.example.crud.entity.Meal;
import com.example.crud.entity.QChef;
import com.example.crud.constants.PageModel;
import com.example.crud.dao.ChefDao;
import com.example.crud.service.base.BaseService;
import com.example.crud.util.MessageResult;

import lombok.Setter;
import java.util.Optional;
import com.example.crud.dao.ChefDao.specs;
import javax.persistence.criteria.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.example.crud.pagination.PageResult;
import com.example.crud.pagination.Restrictions;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import com.example.crud.pagination.Criteria;
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
        return new PageResult<>(list, jpaQuery.fetchCount());//添加总条数
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
    
    // HOW PREDICATE IS BEING USED
    // this should prob be in the controller, no service method should return MessageResult 
    public MessageResult pageQuery(PageModel pageModel, WithdrawRecordScreen screen) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QWithdrawRecord.withdrawRecord.memberId.eq(QMember.member.id));

        if (screen.getMemberId() != null) {
            predicates.add(QWithdrawRecord.withdrawRecord.memberId.eq(screen.getMemberId()));
        }

        if ( !StringUtils.isEmpty(screen.getMobilePhone())){
            Member member = memberService.findByPhone(screen.getMobilePhone());
            predicates.add(QWithdrawRecord.withdrawRecord.memberId.eq(member.getId()));
        }

        if ( !StringUtils.isEmpty(screen.getOrderSn())){
            predicates.add(QWithdrawRecord.withdrawRecord.transactionNumber.eq(screen.getOrderSn()));
        }

        if (screen.getStatus() != null) {
            predicates.add(QWithdrawRecord.withdrawRecord.status.eq(screen.getStatus()));
        }

        if (screen.getIsAuto() != null) {
            predicates.add(QWithdrawRecord.withdrawRecord.isAuto.eq(screen.getIsAuto()));
        }

        if (!StringUtils.isEmpty(screen.getAddress())) {
            predicates.add(QWithdrawRecord.withdrawRecord.address.eq(screen.getAddress()));
        }

        if (!StringUtils.isEmpty(screen.getUnit())) {
            predicates.add(QWithdrawRecord.withdrawRecord.coin.unit.equalsIgnoreCase(screen.getUnit()));
        }

        if (!StringUtils.isEmpty(screen.getAccount())) {
            predicates.add(QMember.member.username.like("%" + screen.getAccount() + "%")
                    .or(QMember.member.realName.like("%" + screen.getAccount() + "%")));
        }

        Page<WithdrawRecordVO> pageListMapResult = withdrawRecordService.joinFind(predicates, pageModel);
        return success(pageListMapResult);
    }

    // HOW ENTITY PATH AND EXPRESSION ARE BEEN USED. HOW queryDslForPageListResult IS BEEN USED(CHECK BASESERVICE)
    // @Transactional(readOnly = true)
    // public void test() {
    //     //查询字段
    //     List<Expression> expressions = new ArrayList<>();
    //     expressions.add(QWithdrawRecord.withdrawRecord.memberId.as("memberId"));
    //     //查询表
    //     List<EntityPath> entityPaths = new ArrayList<>();
    //     entityPaths.add(QWithdrawRecord.withdrawRecord);
    //     entityPaths.add(QMember.member);
    //     //查询条件
    //     List<Predicate> predicates = new ArrayList<>();
    //     predicates.add(QWithdrawRecord.withdrawRecord.memberId.eq(QMember.member.id));
    //     //排序
    //     List<OrderSpecifier> orderSpecifierList = new ArrayList<>();
    //     orderSpecifierList.add(QWithdrawRecord.withdrawRecord.id.desc());
    //     PageListMapResult pageListMapResult = super.queryDslForPageListResult(expressions, entityPaths, predicates, orderSpecifierList, 1, 10);
    //     System.out.println(pageListMapResult);

    // }

    // TASK: USE THE queryDslForPageListResult(QueryDslContext qdc, Integer pageNo, Integer pageSize) OF THIS SIGNATURE 

    // TASK: USE THE PageResult<T> queryDsl(Integer pageNo, Integer pageSize, List<Predicate> predicateList, 
        // EntityPathBase<T> entityPathBase, List<OrderSpecifier> orderSpecifierList) METHOD

    // TASK: USE queryOneDsl(Predicate predicate, EntityPathBase<T> entityPathBase) METHOD

    // Blaze stuff start by reading docs

    // window functions 
    // https://vladmihalcea.com/why-you-should-definitely-learn-sql-window-functions/
    // https://www.youtube.com/watch?v=xFeOVIIRyvQ

    // complex queries
}
