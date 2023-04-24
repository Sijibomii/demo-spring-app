package com.example.crud.constants;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


// read about this
@Data
public class PageModel {
    Integer pageNo = 1;
    Integer pageSize = 10;
    List<Sort.Direction> direction;
    List<String> property;

    public void setSort(){
        if(property==null||property.size()==0){
            List<String> list = new ArrayList<>();
            list.add("id");
            List<Sort.Direction> directions = new ArrayList<>();
            directions.add(Sort.Direction.DESC);
            property = list ;
            direction = directions ;
        }
    }
    private Sort getSort() {
        List<Sort.Order> orders = null;
        setSort();
        if(direction.size() == property.size()) {
            orders = new ArrayList<>();
            int length = direction.size();
            for (int i = 0; i < length; i++) {
                orders.add(new Sort.Order(direction.get(i), property.get(i)));
            }
        }
        return Sort.by(orders);
    }

    public Pageable getPageable() {
        Sort sort = getSort();
        if (sort == null) {
            return  PageRequest.of(pageNo - 1, pageSize);
        }
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    public Order directoryToOrder(Sort.Direction direction){
       return direction.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC;
    }

    public List<Order> toOrders(List<Sort.Direction> list){
        List<Order> orders = new ArrayList<>();
        for(Sort.Direction direction:list){
            orders.add(directoryToOrder(direction));
        }
        return orders ; 
    }

    @SuppressWarnings({ "all" }) // raw types warnings
    public List<OrderSpecifier> getOrderSpecifiers(){
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        // sort by id
        setSort();
        if(this.getProperty()!=null){
            // if the property field is not null
            for(int i = 0 ; i < this.getProperty().size() ;i++){
                Path path = ExpressionUtils.path(Path.class, this.getProperty().get(i));
                // direction is a field: list of Sort.Orders 
                OrderSpecifier orderSpecifier = new OrderSpecifier(this.toOrders(this.getDirection()).get(i), path);
                orderSpecifiers.add(orderSpecifier);
            }
        }
        return orderSpecifiers ;
    }


}
