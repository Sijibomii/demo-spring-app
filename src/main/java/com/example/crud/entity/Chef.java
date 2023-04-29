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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.FetchType;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
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
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name="eatery_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Eatery eatery; 


    @ManyToOne
    @JoinColumn(name="meal_id")
    private Meal meal;

    private double salary;
 
    @NotNull
    private boolean is_male;

    @NotNull
    private boolean is_on_duty;
    
    @Column(columnDefinition = "boolean default false")
    private boolean is_active;

    @NotNull
    @Column(unique = true)
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreationTimestamp
    private Date createdOn;
    
}
