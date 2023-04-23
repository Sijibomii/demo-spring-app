package com.example.crud.pagination;

import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.*;
import org.springframework.data.domain.Sort;

// QueryBuilderUtils
public class Criteria<T> implements Specification<T>{
    
    private List<Criterion> criteria = new ArrayList<Criterion>();

    @Override
    //Creates a WHERE clause for a query of the referenced entity in form of a Predicate for the given Root and CriteriaQuery.
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!criteria.isEmpty()) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            for(Criterion c : criteria){
                predicates.add(c.toPredicate(root, query,builder));
            }
            // Combine all conditions with and
            if (predicates.size() > 0) {
                //Create a conjunction of the given restriction predicates.
                // new Predicate[predicates.size()] is used to specify the type and makes sure predicates.toArray returns a Predicate[] not an Object[]
                // https://docs.oracle.com/javase/8/docs/api/java/util/List.html
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        // return empty conjunction
        return builder.conjunction();
    }

    // Add simple conditional expressions(Criterion) to the criteria list
    public void add(Criterion criterion){
        if(criterion !=null){
            criteria.add(criterion);
        }
    }

    //  sort("name","id.desc"); Indicates ascending order by name first, then descending order by id
    public  Sort sort(String... fields) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        for(String f:fields) {
            orders.add(generateOrderStatic(f));
        }
        return Sort.by(orders);
    }

    // static method
    // ... is used to take in multiple args but must be the last on the signature
    // if more than one field is provided it will be stored in an array i.e sortStatic("sort"), sortStatic("sort", "enable") will both work and return and arr of string
    // field. the length of field depends on the amount of arg passed
    // basically function returns a list of criteria by which ordering should be done.
    // diff columns or atrr and add ordering drn
    public static Sort sortStatic(String... fields) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        for(String f:fields) {
            // for field in fields
            orders.add(generateOrderStatic(f));
        }
        return Sort.by(orders);
    }
    
    // based on string entered it returns an sort order obj
    private static Sort.Order generateOrderStatic(String f) {
        Sort.Order order = null;
        // f.split() -> String.split splits according to a regex. what does the regex do?: it splits according to "."
        // "name" returns just name since no "." i.e ff.length = 1, "id.desc" returns id and desc i.e ff.length = 2
        String[] ff = f.split("\\.");
        // ff contains column or atrr for sorting in index 0 and order in idx 1
        if(ff.length>1) {
            if("desc".equals(ff[1])) {
                order = new Sort.Order(Sort.Direction.DESC,ff[0]);
            } else {
                order = new Sort.Order(Sort.Direction.ASC,ff[0]);
            }
            return order;
        }
        // if no order is specified use default which should be asc if order is null
        order = new Sort.Order(null, f);
        return order;
    }
}
