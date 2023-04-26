package com.example.crud.pagination;

import org.hibernate.criterion.MatchMode;
// import java.util.Collection;

// conditional constructor
// Used to create conditional expressions
public class Restrictions {

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        // simple expression is a class in this package
        // takes field , value and operator
        return new SimpleExpression (fieldName, value, Criterion.Operator.EQ);
    }

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression isNotNull(String fieldName, Object value, boolean ignoreNull) {
        return new SimpleExpression (fieldName,value,Criterion.Operator.ISNOTNULL);
    }

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.NE);
    }

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression like(String fieldName, String value, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LIKE);
    }

    /**
     *
     * @param fieldName
     * @param value
     * @param matchMode
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression like(String fieldName, String value,MatchMode matchMode, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        return null;
    }

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.GT);
    }

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LT);
    }

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.LTE);
    }

    /**
     * @param fieldName
     * @param value
     * @param ignoreNull
     * @return
     */
    public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull) {
        if(value == null) {
            return null;
        }
        return new SimpleExpression (fieldName, value, Criterion.Operator.GTE);
    }

    
    // public static LogicalExpression and(Criterion... pageCriteria){
    //     return new LogicalExpression(pageCriteria, Criterion.Operator.AND);
    // }
   
    // public static LogicalExpression or(Criterion... pageCriteria){
    //     return new LogicalExpression(pageCriteria, Criterion.Operator.OR);
    // }
    
    // @SuppressWarnings("rawtypes")
    // public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
    //     if(ignoreNull&&(value==null||value.isEmpty())){
    //         return null;
    //     }
    //     SimpleExpression[] ses = new SimpleExpression[value.size()];
    //     int i=0;
    //     for(Object obj : value){
    //         ses[i]=new SimpleExpression(fieldName,obj, Criterion.Operator.EQ);
    //         i++;
    //     }
    //     return new LogicalExpression(ses, Criterion.Operator.OR);
    // }
}