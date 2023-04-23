package com.example.crud.constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.example.crud.constants.base.BaseEnum;

@AllArgsConstructor
@Getter
public enum OrderStatus implements BaseEnum{

    CANCELLED("Cancelled"),
   
    NONPAYMENT("Unpaid"),
    
    PAID("Paid"),
    
    COMPLETED("Completed"),
    
    APPEAL("Appeal");

    @Setter
    private String name;

    @Override
    @JsonValue
    public int getOrdinal(){
        return this.ordinal();
    }
}
