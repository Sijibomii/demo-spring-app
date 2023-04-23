package com.example.crud.pagination;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

public class SimpleExpression implements Criterion {

    private String fieldName;       // attribute name
    private Object value;           // corresponding value
    private Operator operator;      // calculator

    protected SimpleExpression(String fieldName, Object value, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldName;
    }
    public Object getValue() {
        return value;
    }
    public Operator getOperator() {
        return operator;
    }
    // what i'm looking for
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,CriteriaBuilder builder) {
        //  property path String like "customer.order.price"
        Path expression = null;
        if(fieldName.contains(".")){
            String[] names = StringUtils.split(fieldName, ".");
            // root i.e the entity it self
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                // traverse the path and to the end
                expression = expression.get(names[i]);
            }
        }else{
            expression = root.get(fieldName);
        }
        // construct the query predicate here
        switch (operator) {
            case EQ:
                return builder.equal(expression, value);
            case NE:
                return builder.notEqual(expression, value);
            case LIKE:
                return builder.like((Expression<String>) expression, "%" + value + "%");
            case LT:
                return builder.lessThan(expression, (Comparable) value);
            case GT:
                return builder.greaterThan(expression, (Comparable) value);
            case LTE:
                return builder.lessThanOrEqualTo(expression, (Comparable) value);
            case GTE:
                return builder.greaterThanOrEqualTo(expression, (Comparable) value);
            case ISNOTNULL:
                return builder.isNotNull(expression);
            default:
                return null;
        }
    }

}