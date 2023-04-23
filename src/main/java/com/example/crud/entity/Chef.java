package com.example.crud.entity;

import javax.persistence.Entity;
import javax.persistence.*;
// import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Entity
@Data
@Table
public class Chef {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String name;

    // multiple chefs in an eatery
    // this means we are only able to access the eatery associated with a chef from the chef entity
    // if we want to be able to access the chef associated with a eatry we use a onetomany mappring and make it bi directional
    @ManyToOne
    @JoinColumn(name="eatery_id")
    private Eatery eatery; 


    @ManyToOne
    @JoinColumn(name="meal_id")
    private Meal meal;

    private double salary;
 
    @NotNull
    private boolean is_male;

    @NotNull
    private boolean is_on_duty;

    @NotNull
    @Column(unique = true)
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdOn;
    
}
