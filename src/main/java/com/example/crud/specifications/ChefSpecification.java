// package com.example.crud.specifications;

// import com.querydsl.core.types.Predicate;
// import javax.persistence.criteria.*;
// import com.example.crud.entity.Chef;
// import org.springframework.data.jpa.domain.Specification;

// public class ChefSpecification implements Specification<Chef>  {
    
//     public static Specification<Chef> idEquals(Long id) {

//         return new  implements Specification<Chef>(){
//             @Override
//             public Predicate toPredicate(Root<E> root, CriteriaQuery query, CriteriaBuilder cb) {
//                 // return cb.equal(root.get(Chef.getId()), id);
//             }
//         };
//     }
//     @Override
//     public Predicate toPredicate(Root<Chef> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
 
//         // if (criteria.getOperation().equalsIgnoreCase(">")) {
//         //     return builder.greaterThanOrEqualTo(
//         //       root.<String> get(criteria.getKey()), criteria.getValue().toString());
//         // } 
//         // else if (criteria.getOperation().equalsIgnoreCase("<")) {
//         //     return builder.lessThanOrEqualTo(
//         //       root.<String> get(criteria.getKey()), criteria.getValue().toString());
//         // } 
//         // else if (criteria.getOperation().equalsIgnoreCase(":")) {
//         //     if (root.get(criteria.getKey()).getJavaType() == String.class) {
//         //         return builder.like(
//         //           root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
//         //     } else {
//         //         return builder.equal(root.get(criteria.getKey()), criteria.getValue());
//         //     }
//         // }
//         return null;
//     }
// }
